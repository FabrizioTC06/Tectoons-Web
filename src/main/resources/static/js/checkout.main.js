document.addEventListener('DOMContentLoaded', async () => {
  const resumenContainer = document.getElementById('checkout-resumen');
  const totalElement = document.getElementById('checkout-total');
  const form = document.getElementById('checkout-form');

  async function cargarResumen() {
    try {
      const res = await fetch('/tienda/api/carrito');
      const data = await res.json();

      resumenContainer.innerHTML = '';
      if (!data.items || data.items.length === 0) {
        resumenContainer.innerHTML = '<p>Tu carrito está vacío.</p>';
        totalElement.textContent = 'S/ 0.00';
        form.style.display = 'none';
        return;
      }

      data.items.forEach(item => {
        const div = document.createElement('div');
        div.classList.add('checkout-item');
        div.innerHTML = `
          <span>${item.nombre} (x${item.cantidad})</span>
          <span>S/ ${item.subtotal.toFixed(2)}</span>
        `;
        resumenContainer.appendChild(div);
      });

      totalElement.textContent = `S/ ${data.total.toFixed(2)}`;
    } catch (err) {
      console.error('Error al cargar el resumen del checkout:', err);
    }
  }

  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const datos = {
      nombre: form.nombre.value.trim(),
      correo: form.correo.value.trim(),
      direccion: form.direccion.value.trim()
    };

    if (!datos.nombre || !datos.correo || !datos.direccion) {
      alert('Por favor, completa todos los campos.');
      return;
    }

    try {
      const res = await fetch('/tienda/api/checkout', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(datos)
      });

      const result = await res.json();
      if (result.status === 'success') {
        alert(`Compra completada 🎉\nPedido #${result.pedidoId}`);
        window.location.href = '/tienda';
      }
    } catch (err) {
      console.error('Error en el checkout:', err);
    }
  });

  cargarResumen();
});
