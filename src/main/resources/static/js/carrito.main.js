document.addEventListener('DOMContentLoaded', async () => {
  const carritoContainer = document.getElementById('carrito-container');
  const totalElement = document.getElementById('carrito-total');
  const vacioMsg = document.getElementById('carrito-vacio');

  async function cargarCarrito() {
    try {
      const res = await fetch('/tienda/api/carrito');
      const data = await res.json();

      carritoContainer.innerHTML = '';
      if (!data.items || data.items.length === 0) {
        vacioMsg.style.display = 'block';
        totalElement.textContent = 'S/ 0.00';
        return;
      }

      vacioMsg.style.display = 'none';

      data.items.forEach(item => {
        const card = document.createElement('div');
        card.classList.add('carrito-item');
        card.innerHTML = `
          <img src="${item.imagen}" alt="${item.nombre}">
          <div class="info">
            <h4>${item.nombre}</h4>
            <p>${item.autor}</p>
            <p>Precio: S/ ${item.precio.toFixed(2)}</p>
            <p>Cantidad: ${item.cantidad}</p>
            <p><strong>Subtotal: S/ ${item.subtotal.toFixed(2)}</strong></p>
            <button class="btn-eliminar" data-id="${item.id}">Eliminar</button>
          </div>
        `;
        carritoContainer.appendChild(card);
      });

      totalElement.textContent = `S/ ${data.total.toFixed(2)}`;
    } catch (err) {
      console.error('Error al cargar el carrito:', err);
    }
  }

  carritoContainer.addEventListener('click', async (e) => {
    if (e.target.classList.contains('btn-eliminar')) {
      const id = e.target.dataset.id;
      const res = await fetch('/tienda/api/carrito/remove', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ id: parseInt(id) })
      });
      const result = await res.json();
      if (result.ok) cargarCarrito();
    }
  });

  document.getElementById('btn-ir-checkout').addEventListener('click', () => {
    window.location.href = '/tienda/checkout';
  });

  // Inicial
  cargarCarrito();
});
