/**
 * blog.main.js
 * - Filtrado por categoría (pills)
 * - Búsqueda por título/autor
 * - Paginación client-side
 * - Reveal (IntersectionObserver)
 * - Subscribe form validation + toast
 *
 * Nota: La página funciona sin JS (progressive enhancement),
 * este script mejora la experiencia.
 */

/* Sample posts - reemplaza con fetch a /api/blog si tienes backend */
const samplePosts = [
  {
    id: 1, title: "Making of: Robuny - diseño del personaje",
    author: "Equipo TecToons", category: "makingof",
    excerpt: "Cómo nació el personaje Robuny: bocetos, colores y movimiento.",
    date: "2025-09-10", tags: ["makingof","personajes"],
    image: "https://picsum.photos/id/1011/800/500"
  },
  {
    id: 2, title: "Tutorial: Iluminación para ilustración digital",
    author: "Marta Diaz", category: "tutorial",
    excerpt: "Técnicas sencillas para dar volumen y ambiente con luz.",
    date: "2025-10-02", tags: ["tutorial","ilustracion"],
    image: "https://picsum.photos/id/1025/800/500"
  },
  {
    id: 3, title: "News: TecToons en la Feria Creativa",
    author: "Redacción", category: "news",
    excerpt: "Anunciamos nuestra participación en la feria local este noviembre.",
    date: "2025-08-20", tags: ["evento","noticias"],
    image: "https://picsum.photos/id/1035/800/500"
  },
  {
    id: 4, title: "Proceso: Animación 2D frame-by-frame",
    author: "Lucas R.", category: "trending",
    excerpt: "Un vistazo rápido al proceso tradicional adaptado a digital.",
    date: "2025-07-19", tags: ["animacion","makingof"],
    image: "https://picsum.photos/id/1041/800/500"
  },
  {
    id: 5, title: "Latest: Nueva serie 'Robuny' - avance",
    author: "Equipo TecToons", category: "latest",
    excerpt: "Primer adelanto de la serie Robuny: personajes y mundo.",
    date: "2025-10-10", tags: ["serie","avance"],
    image: "https://picsum.photos/id/1052/800/500"
  },
  {
    id: 6, title: "Toolkit: Recursos útiles para ilustradores",
    author: "Ana P.", category: "tutorial",
    excerpt: "Packs, brushes y referencias para acelerar tu flujo creativo.",
    date: "2025-06-05", tags: ["recursos","tutorial"],
    image: "https://picsum.photos/id/1062/800/500"
  }
];

// Variables UI
const postsGrid = document.getElementById('postsGrid');
const paginationEl = document.getElementById('pagination');
const recentList = document.getElementById('recentList');
const searchInput = document.getElementById('searchInput');
const categoryPills = document.getElementById('categoryPills');

let currentCategory = 'all';
let currentPage = 1;
const perPage = 4;
let filtered = samplePosts.slice(); // copia inicial

// Utility: create a post card element
function createPostCard(post) {
  const col = document.createElement('div');
  col.className = 'col-12 reveal';

  col.innerHTML = `
    <article class="post-card row g-0" role="article" aria-labelledby="post-title-${post.id}">
      <div class="col-md-5">
        <img class="thumb" src="${post.image}" alt="${escapeHtml(post.title)}" loading="lazy" width="800" height="500">
      </div>
      <div class="col-md-7">
        <div class="card-body">
          <h3 id="post-title-${post.id}" class="post-title"><a href="/blog/${post.id}" class="text-decoration-none text-dark">${escapeHtml(post.title)}</a></h3>
          <div class="post-meta small mb-2">${formatDate(post.date)} • por ${escapeHtml(post.author)} • <span class="text-primary">${escapeHtml(post.category)}</span></div>
          <p class="mb-2 text-muted">${escapeHtml(post.excerpt)}</p>
          <div>
            ${post.tags.map(t => `<span class="badge bg-light text-muted me-1">${escapeHtml(t)}</span>`).join('')}
            <a href="/blog/${post.id}" class="btn btn-sm btn-outline-primary float-end">Leer más</a>
          </div>
        </div>
      </div>
    </article>
  `;
  return col;
}

function escapeHtml(s) {
  return (s||'').replace(/[&<>"'`=\/]/g, function(ch){
    return ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;','/':'&#47;','`':'&#96;','=':'&#61;'}[ch]);
  });
}

function formatDate(d) {
  try {
    const dt = new Date(d);
    return dt.toLocaleDateString('es-PE', { year: 'numeric', month: 'short', day: 'numeric' });
  } catch(e){ return d; }
}

// RENDER paginated
function renderPosts() {
  postsGrid.innerHTML = '';
  const start = (currentPage -1) * perPage;
  const pageItems = filtered.slice(start, start + perPage);
  if (pageItems.length === 0) {
    postsGrid.innerHTML = `<div class="col-12"><p class="text-muted">No se encontraron publicaciones.</p></div>`;
  } else {
    pageItems.forEach(p => {
      postsGrid.appendChild(createPostCard(p));
    });
  }
  renderPagination();
  observeReveal(); // inicializa reveal para nuevos elementos
}

// PAGINACIÓN
function renderPagination() {
  paginationEl.innerHTML = '';
  const totalPages = Math.ceil(filtered.length / perPage) || 1;
  const createPageItem = (n, label = n, active = false) => {
    const li = document.createElement('li');
    li.className = 'page-item' + (active ? ' active' : '');
    li.innerHTML = `<a class="page-link" href="#" data-page="${n}">${label}</a>`;
    return li;
  };

  // Prev
  const prev = document.createElement('li');
  prev.className = 'page-item' + (currentPage === 1 ? ' disabled' : '');
  prev.innerHTML = `<a class="page-link" href="#" data-page="${Math.max(1, currentPage-1)}">Anterior</a>`;
  paginationEl.appendChild(prev);

  // Pages (simple: show up to 5)
  const startPage = Math.max(1, currentPage - 2);
  const endPage = Math.min(totalPages, startPage + 4);
  for (let i = startPage; i <= endPage; i++) {
    paginationEl.appendChild(createPageItem(i, i, i===currentPage));
  }

  // Next
  const next = document.createElement('li');
  next.className = 'page-item' + (currentPage === totalPages ? ' disabled' : '');
  next.innerHTML = `<a class="page-link" href="#" data-page="${Math.min(totalPages, currentPage+1)}">Siguiente</a>`;
  paginationEl.appendChild(next);

  // attach listener
  paginationEl.querySelectorAll('.page-link').forEach(a => {
    a.addEventListener('click', (e) => {
      e.preventDefault();
      const p = parseInt(a.dataset.page);
      if (!isNaN(p) && p !== currentPage) {
        currentPage = p;
        renderPosts();
        window.scrollTo({ top: 200, behavior: 'smooth' });
      }
    });
  });
}

// FILTERING
function applyFilters() {
  const q = (searchInput?.value || '').toLowerCase().trim();
  filtered = samplePosts.filter(p => {
    const matchCat = currentCategory === 'all' || p.category === currentCategory;
    const matchQ = !q || p.title.toLowerCase().includes(q) || p.author.toLowerCase().includes(q);
    return matchCat && matchQ;
  });
  currentPage = 1;
  renderPosts();
  renderRecentList();
}

// CATEGORY PILL LISTENERS
if (categoryPills) {
  categoryPills.querySelectorAll('a[data-cat]').forEach(a => {
    a.addEventListener('click', (e) => {
      e.preventDefault();
      categoryPills.querySelectorAll('.nav-link').forEach(n => n.classList.remove('active'));
      a.classList.add('active');
      currentCategory = a.dataset.cat;
      applyFilters();
    });
  });
}

// SEARCH LISTENER
if (searchInput) {
  searchInput.addEventListener('input', () => {
    applyFilters();
  });
}

// RENDER recent items in sidebar
function renderRecentList() {
  if (!recentList) return;
  recentList.innerHTML = '';
  const recent = samplePosts.slice().sort((a,b) => new Date(b.date) - new Date(a.date)).slice(0,4);
  recent.forEach(p => {
    const li = document.createElement('li');
    li.className = 'd-flex align-items-center mb-2';
    li.innerHTML = `
      <img src="${p.image}" alt="${escapeHtml(p.title)}" loading="lazy" width="48" height="48" class="me-2">
      <div><a class="text-decoration-none text-dark" href="/blog/${p.id}">${escapeHtml(p.title)}</a><div class="small text-muted">${formatDate(p.date)}</div></div>
    `;
    recentList.appendChild(li);
  });
}

// Reveal animation with IntersectionObserver
function observeReveal() {
  const obs = new IntersectionObserver((entries, observer) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('visible');
        observer.unobserve(entry.target);
      }
    });
  }, {threshold: 0.12});
  document.querySelectorAll('.reveal').forEach(el => obs.observe(el));
}

// Subscribe form
(function subscribeHandler(){
  const form = document.getElementById('subscribeForm');
  const email = document.getElementById('subscribeEmail');
  const toastEl = document.getElementById('subscribeToast');
  const toast = new bootstrap.Toast(toastEl);

  if (!form) return;
  form.addEventListener('submit', (e) => {
    e.preventDefault();
    const val = (email.value || '').trim();
    if (!val || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(val)) {
      email.classList.add('is-invalid');
      setTimeout(()=> email.classList.remove('is-invalid'), 1500);
      return;
    }
    // Simular envío
    email.value = '';
    toast.show();
  });
})();

// Category links in sidebar
document.querySelectorAll('.category-link').forEach(a => {
  a.addEventListener('click', (e) => {
    e.preventDefault();
    const cat = a.dataset.cat;
    // activate pill
    document.querySelectorAll('#categoryPills .nav-link').forEach(n => n.classList.remove('active'));
    const pill = document.querySelector(`#categoryPills .nav-link[data-cat="${cat}"]`);
    if (pill) pill.classList.add('active');
    currentCategory = cat;
    applyFilters();
    window.scrollTo({ top: 200, behavior: 'smooth' });
  });
});

// Buttons New/Import demo
document.getElementById('btnNewPost')?.addEventListener('click', () => alert('Demo: crear nuevo post (solo frontend)'));
document.getElementById('btnImport')?.addEventListener('click', () => alert('Demo: importar posts (solo frontend)'));

// Inicial render
applyFilters();
renderRecentList();
