/**
 * tienda.main.js
 * - carga productos desde /tienda/api/productos y renderiza la grid
 * - filtros, búsqueda, "ver" (modal) y carrito (llamadas POST a /tienda/api/carrito/add)
 * - actualiza el badge del carrito desde /tienda/api/carrito
 */

document.addEventListener('DOMContentLoaded', () => {
  const API_PRODUCTS = '/tienda/api/productos';
  const API_CART = '/tienda/api/carrito';
  const API_ADD = '/tienda/api/carrito/add';
  const API_REMOVE = '/tienda/api/carrito/remove';
  const API_CHECKOUT = '/tienda/api/checkout';

  const grid = document.getElementById('productosGrid');
  const searchInput = document.getElementById('searchInput');
  const filtroCategoria = document.getElementById('filtroCategoria');
  const ordenarPor = document.getElementById('ordenarPor');
  const cargarMasBtn = document.getElementById('cargarMas');

  // modal elements
  const vistaRapidaModal = new bootstrap.Modal(document.getElementById('vistaRapidaModal'));
  const modalImagen = document.getElementById('modalImagen');
  const modalNombre = document.getElementById('modalNombre');
  const modalAutor = document.getElementById('modalAutor');
  const modalPrecio = document.getElementById('modalPrecio');
  const modalDescripcion = document.getElementById('modalDescripcion');
  const btnAgregarModal = document.getElementById('btnAgregarModal');

  const cartCountEl = document.getElementById('cart-count');

  let products = [];       // lista completa desde API
  let displayed = [];      // items filtrados
  let page = 1;
  const perPage = 8;

  // fetch products from API; if fails, leave server-rendered DOM as-is
  fetch(API_PRODUCTS)
    .then(r => r.json())
    .then(data => {
      products = data;
      displayed = products.slice();
      page = 1;
      renderPage();
      attachGlobalListeners();
      refreshCartBadge();
    })
    .catch(err => {
      console.warn('No se pudo cargar productos desde API, se usará render server-side si existe.', err);
      attachGlobalListeners(); // still attach behavior to server-side buttons
      refreshCartBadge();
    });

  // render paginated
  function renderPage(reset = true) {
    if (reset) grid.innerHTML = '';
    const start = (page - 1) * perPage;
    const slice = displayed.slice(start, start + perPage);
    slice.forEach(p => {
      const col = document.createElement('div');
      col.className = 'col-12 col-sm-6 col-md-4 col-lg-3';
      col.innerHTML = productCardHtml(p);
      grid.appendChild(col);
    });
    // hide load more if no more
    cargarMasBtn.style.display = (page * perPage < displayed.length) ? 'inline-block' : 'none';
    attachCardListeners();
  }

  function productCardHtml(p) {
    const badge = p.etiqueta ? `<span class="badge bg-danger position-absolute top-0 start-0 m-2">${escapeHtml(p.etiqueta)}</span>` : '';
    return `
      <div class="card h-100 producto-card shadow-sm border-0">
        <div class="position-relative">
          <img src="${escapeHtml(p.imagen)}" alt="${escapeHtml(p.nombre)}" loading="lazy" class="card-img-top">
          ${badge}
        </div>
        <div class="card-body text-center">
          <h5 class="card-title">${escapeHtml(p.nombre)}</h5>
          <p class="text-muted mb-1">Por ${escapeHtml(p.autor)}</p>
          <p class="fw-semibold fs-5 text-primary">S/ ${Number(p.precio).toFixed(2)}</p>
        </div>
        <div class="card-footer bg-transparent border-0 text-center">
          <button class="btn btn-outline-primary btn-sm ver-producto" data-id="${p.id}"><i class="bi bi-eye"></i> Ver</button>
          <button class="btn btn-primary btn-sm agregar-carrito" data-id="${p.id}"><i class="bi bi-cart-plus"></i> Añadir</button>
        </div>
      </div>
    `;
  }

  // attach clicks for dynamically rendered cards
  function attachCardListeners() {
    grid.querySelectorAll('.agregar-carrito').forEach(btn => {
      btn.removeEventListener('click', onAddCart);
      btn.addEventListener('click', onAddCart);
    });
    grid.querySelectorAll('.ver-producto').forEach(btn => {
      btn.removeEventListener('click', onVerProducto);
      btn.addEventListener('click', onVerProducto);
    });
  }

  // if page was rendered via Thymeleaf (server-side), attach behavior to those buttons too
  function attachGlobalListeners() {
    // search
    if (searchInput) {
      searchInput.addEventListener('input', debounce(() => {
        applyFilters();
      }, 200));
    }
    if (filtroCategoria) filtroCategoria.addEventListener('change', applyFilters);
    if (ordenarPor) ordenarPor.addEventListener('change', applyFilters);
    if (cargarMasBtn) cargarMasBtn.addEventListener('click', () => { page++; renderPage(false); });
  }

  function applyFilters() {
    const q = (searchInput?.value || '').toLowerCase().trim();
    const cat = (filtroCategoria?.value || 'todos').toLowerCase();
    const sort = (ordenarPor?.value || 'default');

    displayed = products.filter(p => {
      const matchesQ = !q || p.nombre.toLowerCase().includes(q) || p.autor.toLowerCase().includes(q);
      const matchesCat = (cat === 'todos') || (p.categoria && p.categoria.toLowerCase() === cat);
      return matchesQ && matchesCat;
    });

    if (sort === 'precioAsc') displayed.sort((a,b)=> a.precio - b.precio);
    if (sort === 'precioDesc') displayed.sort((a,b)=> b.precio - a.precio);
    if (sort === 'nuevo') displayed = displayed.slice().reverse();

    page = 1;
    renderPage();
  }

  // Add to cart
  function onAddCart(e) {
    const id = Number(e.currentTarget.dataset.id);
    fetch(API_ADD, {
      method: 'POST',
      headers: {'Content-Type':'application/json'},
      body: JSON.stringify({id: id, qty: 1})
    })
      .then(r => r.json())
      .then(json => {
        if (json.ok) {
          refreshCartBadge();
          // small feedback
          e.currentTarget.classList.add('btn-success');
          e.currentTarget.innerHTML = '<i class="bi bi-check-lg"></i> Añadido';
          setTimeout(()=> {
            e.currentTarget.classList.remove('btn-success');
            e.currentTarget.innerHTML = '<i class="bi bi-cart-plus"></i> Añadir';
          }, 900);
        }
      })
      .catch(err => console.error('Error add cart', err));
  }

  // View product in modal
  function onVerProducto(e) {
    const id = Number(e.currentTarget.dataset.id);
    const p = products.find(x => x.id === id);
    if (!p) return;
    modalImagen.src = p.imagen;
    modalNombre.textContent = p.nombre;
    modalAutor.textContent = 'Por ' + p.autor;
    modalPrecio.textContent = 'S/ ' + Number(p.precio).toFixed(2);
    modalDescripcion.textContent = p.descripcion || '';
    btnAgregarModal.onclick = () => {
      fetch(API_ADD, {
        method: 'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify({id: p.id, qty: 1})
      }).then(r=>r.json()).then(j => { if (j.ok) refreshCartBadge(); vistaRapidaModal.hide(); });
    };
    vistaRapidaModal.show();
  }

  // refresh cart count from API
  function refreshCartBadge(){
    fetch(API_CART)
      .then(r => r.json())
      .then(json => {
        const n = json.totalQty || 0;
        cartCountEl.textContent = n;
      })
      .catch(err => console.warn('No se pudo actualizar badge', err));
  }

  // util
  function escapeHtml(unsafe){
    return (unsafe+'').replace(/[&<>"'`=\/]/g, function(s){
      return ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;','/':'&#47;','`':'&#96;','=':'&#61;'})[s];
    });
  }

  function debounce(fn, wait){
    let t;
    return (...a)=> { clearTimeout(t); t=setTimeout(()=>fn.apply(this,a), wait); };
  }

});
