/**
 * artistas.main.js
 * - Consume /artistas/api -> (fetch('/artistas/api/artistas') ) y renderiza la grid
 * - Implementa: búsqueda, filtro, paginación "load more", follow (localStorage), reveal animations
 * - Progressive enhancement: si fetch falla, deja el HTML pre-renderizado (Thymeleaf)
 */

document.addEventListener('DOMContentLoaded', () => {
  const API_LIST = '/artistas/api/artistas';
  const grid = document.getElementById('artistsGrid');
  const searchEl = document.getElementById('searchArtists');
  const filterEl = document.getElementById('filterCategory');
  const loadMoreBtn = document.getElementById('loadMore');

  let artists = [];      // todos los artistas traídos del servidor
  let filtered = [];     // resultado tras filtros
  let page = 1;
  const perPage = 6;

  // follow state en localStorage: Set de ids seguidos
  const followsKey = 'tectoons_follows';
  const getFollows = () => new Set(JSON.parse(localStorage.getItem(followsKey) || '[]'));
  const saveFollows = s => localStorage.setItem(followsKey, JSON.stringify([...s]));

  // Try to fetch artists list
  fetch(API_LIST)
    .then(r => {
      if (!r.ok) throw new Error('no ok');
      return r.json();
    })
    .then(data => {
      artists = data;
      filtered = artists.slice();
      page = 1;
      renderPage(true);
      initUI();
    })
    .catch(err => {
      console.warn('No se pudo obtener artistas por API (usar fallback Thymeleaf):', err);
      // attach follow handlers for server-rendered buttons
      initUI();
    });

  // Render paginated
  function renderPage(reset = true){
    if (!grid) return;
    if (reset) grid.innerHTML = '';

    const start = (page-1)*perPage;
    const slice = filtered.slice(start, start + perPage);

    slice.forEach(a => {
      const col = document.createElement('div');
      col.className = 'col-md-4';
      col.innerHTML = artistCardHtml(a);
      grid.appendChild(col);
    });

    // load more visibility
    if ((page * perPage) >= filtered.length) loadMoreBtn.classList.add('hidden');
    else loadMoreBtn.classList.remove('hidden');

    attachCardListeners();
    revealCards();
  }

  function artistCardHtml(a){
    // category is mocked by checking name (in a real app use a property)
    return `
      <article class="card artist-card h-100" role="article" data-id="${a.id}">
        <div class="card-body d-flex flex-column">
          <div class="d-flex align-items-center gap-3">
            <img src="${escape(a.avatar)}" alt="${escape(a.nombre)}" class="avatar rounded-circle" loading="lazy" width="72" height="72">
            <div>
              <h3 class="h6 mb-1">${escape(a.nombre)}</h3>
              <p class="text-muted small mb-0">${escape(a.ubicacion || '')}</p>
            </div>
          </div>

          <p class="mt-3 text-muted small">${escape(a.bio || '')}</p>

          <div class="mt-auto d-flex gap-2">
            <a href="/artistas/${a.id}" class="btn btn-outline-primary btn-sm w-100">Ver perfil</a>
            <button class="btn btn-primary btn-sm follow-btn" data-id="${a.id}" aria-pressed="false">
              <i class="bi bi-heart"></i> Seguir
            </button>
          </div>
        </div>
      </article>
    `;
  }

  // Attach event handlers to dynamic DOM (follow, view)
  function attachCardListeners(){
    grid.querySelectorAll('.follow-btn').forEach(btn => {
      btn.removeEventListener('click', onFollow);
      btn.addEventListener('click', onFollow);
      // restore state
      const id = btn.dataset.id && Number(btn.dataset.id);
      if (getFollows().has(String(id))) {
        btn.classList.add('following');
        btn.innerHTML = '<i class="bi bi-heart-fill"></i> Siguiendo';
        btn.setAttribute('aria-pressed', 'true');
      } else {
        btn.classList.remove('following');
        btn.innerHTML = '<i class="bi bi-heart"></i> Seguir';
        btn.setAttribute('aria-pressed', 'false');
      }
    });
  }

  function onFollow(e){
    const btn = e.currentTarget;
    const id = String(btn.dataset.id);
    const set = getFollows();
    if (set.has(id)) {
      set.delete(id);
      btn.classList.remove('following');
      btn.innerHTML = '<i class="bi bi-heart"></i> Seguir';
      btn.setAttribute('aria-pressed', 'false');
    } else {
      set.add(id);
      btn.classList.add('following');
      btn.innerHTML = '<i class="bi bi-heart-fill"></i> Siguiendo';
      btn.setAttribute('aria-pressed', 'true');
    }
    saveFollows(set);
  }

  // UI wiring for search/filter/load more
  function initUI(){
    if (searchEl) {
      searchEl.addEventListener('input', debounce(() => {
        applyFilters();
      }, 250));
    }
    if (filterEl) filterEl.addEventListener('change', applyFilters);
    if (loadMoreBtn) loadMoreBtn.addEventListener('click', () => { page++; renderPage(false); });
    // initial attach for server-rendered follow buttons
    attachCardListeners();
    revealCards();
  }

  function applyFilters(){
    const q = (searchEl?.value || '').toLowerCase().trim();
    const cat = (filterEl?.value || 'all').toLowerCase();
    filtered = artists.filter(a => {
      const matchesQ = !q || (a.nombre && a.nombre.toLowerCase().includes(q)) || (a.bio && a.bio.toLowerCase().includes(q));
      // category mock: always true for demo; implement real property for production
      const matchesCat = (cat === 'all') || (a.bio && a.bio.toLowerCase().includes(cat)) ;
      return matchesQ && matchesCat;
    });
    page = 1;
    renderPage(true);
  }

  // IntersectionObserver reveal
  function revealCards(){
    const cards = document.querySelectorAll('.artist-card');
    const obs = new IntersectionObserver((entries, o) => {
      entries.forEach(en => {
        if (en.isIntersecting) {
          en.target.classList.add('fade-in-up');
          o.unobserve(en.target);
        }
      });
    }, { threshold: 0.15 });
    cards.forEach(c => obs.observe(c));
  }

  // helpers
  function escape(s){ return (s||'').replace(/[&<>"'`=\/]/g, function(ch){ return {'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;','/':'&#47;','`':'&#96;','=':'&#61;'}[ch]; }); }
  function debounce(fn, wait){ let t; return (...a) => { clearTimeout(t); t = setTimeout(()=>fn.apply(this,a), wait); }; }
});

//CONFETTI----------------------------------------------------------------------
  const confettiContainer = document.querySelector('.confetti-rain');

  const colors = ['#ff4081', '#00e5ff', '#ffd740', '#69f0ae', '#7c4dff'];

  for (let i = 0; i < 100; i++) {
    const piece = document.createElement('div');
    piece.classList.add('confetti-piece');

    // Posición horizontal aleatoria
    piece.style.left = Math.random() * 100 + 'vw';

    // Duración aleatoria
    piece.style.animationDuration = 3 + Math.random() * 3 + 's';

    // Retraso aleatorio
    piece.style.animationDelay = Math.random() * 5 + 's';

    // Color aleatorio
    piece.style.backgroundColor = colors[Math.floor(Math.random() * colors.length)];

    // Tamaño aleatorio
    const size = Math.random() * 6 + 6;
    piece.style.width = size + 'px';
    piece.style.height = size * 1.5 + 'px';

    confettiContainer.appendChild(piece);
  }

//cards----------------------------------------------------------------------

document.addEventListener("DOMContentLoaded", () => {
  // Carrusel de miniaturas
  document.querySelectorAll('.artist-works-overlay').forEach(container => {
    const images = container.querySelectorAll('img');
    if (images.length > 1) {
      let index = 0;
      images.forEach((img, i) => img.style.display = i === 0 ? 'block' : 'none');
      setInterval(() => {
        images.forEach((img, i) => img.style.display = (i === index ? 'block' : 'none'));
        index = (index + 1) % images.length;
      }, 1800);
    }
  });

  // Botón "Seguir / Siguiendo 💗"
  document.querySelectorAll('.btn-follow').forEach(btn => {
    btn.addEventListener('click', e => {
      e.preventDefault();
      if (btn.classList.contains('following')) {
        btn.classList.remove('following');
        btn.textContent = 'Seguir';
      } else {
        btn.classList.add('following');
        btn.textContent = 'Siguiendo 💗';
      }
    });
  });
});


