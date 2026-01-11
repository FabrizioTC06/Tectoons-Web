const productoModal = new bootstrap.Modal('#productoModal');
const eliminarModal = new bootstrap.Modal('#eliminarModal');
let idProductoAEliminar = null;

const toastDiv = document.getElementById("toastMensaje");
const toast = new bootstrap.Toast(toastDiv);

function mostrarToast(msg, tipo="success") {
    const body = document.getElementById("toastBody");
    body.textContent = msg;

    toastDiv.classList.remove("bg-success", "bg-danger", "bg-info");
    toastDiv.classList.add(
        tipo === "success" ? "bg-success" :
        tipo === "error" ? "bg-danger" : "bg-info"
    );

    toastDiv.classList.remove("animate__fadeInRight", "animate__fadeOutRight");
    toastDiv.classList.add("animate__fadeInRight");

    toast._config.delay = 4000;
    toast.show();

    setTimeout(() => {
        toastDiv.classList.remove("animate__fadeInRight");
        toastDiv.classList.add("animate__fadeOutRight");
    }, 3800);
}

/* Abrir modal */
document.getElementById("btnNuevo").addEventListener("click", () => abrirModal());

function abrirModal(producto = null) {
    document.getElementById("formProducto").reset();
    document.getElementById("productoId").value = producto ? producto.id : "";

    document.getElementById("titulo").value = producto?.titulo ?? "";
    document.getElementById("categoria").value = producto?.categoria ?? "";
    document.getElementById("precio").value = producto?.precio ?? "";
    document.getElementById("imagenUrl").value = producto?.imagenUrl ?? "";
    document.getElementById("descripcion").value = producto?.descripcion ?? "";

    document.getElementById("errorPrecio").classList.add("d-none");
    document.getElementById("precio").classList.remove("is-invalid");

    const stockActualInput = document.getElementById("stockActual");
    const alertaStock = document.getElementById("alertaStock");

    if (producto) {
        stockActualInput.value = producto.stock;
        alertaStock.classList.toggle("d-none", producto.stock > 5);
    } else {
        stockActualInput.value = 0;
        alertaStock.classList.add("d-none");
    }

    document.getElementById("stockAgregar").value = 0;
    document.getElementById("modalTitle").textContent = producto ? "Editar Producto" : "Nuevo Producto";
    productoModal.show();
}

/* Guardar */
document.getElementById("formProducto").addEventListener("submit", async e => {
    e.preventDefault();

    const id = document.getElementById("productoId").value;
    const stockActual = parseInt(document.getElementById("stockActual").value);
    const stockAgregar = parseInt(document.getElementById("stockAgregar").value);
    const totalStock = stockActual + (stockAgregar || 0);

    const precioInput = document.getElementById("precio");
    const precioVal = parseFloat(precioInput.value);
    const errorPrecio = document.getElementById("errorPrecio");

    errorPrecio.classList.add("d-none");
    precioInput.classList.remove("is-invalid");

    if (isNaN(precioVal) || precioVal <= 0) {
        errorPrecio.classList.remove("d-none");
        precioInput.classList.add("is-invalid");
        return;
    }

    if (totalStock < 0) {
        mostrarToast("Stock inválido", "error");
        return;
    }

    const data = {
        titulo: titulo.value,
        categoria: categoria.value,
        precio: precioVal,
        stock: totalStock,
        imagenUrl: imagenUrl.value,
        descripcion: descripcion.value
    };

    try {
        if (id) {
            await axios.put(`/api/artista/productos/${id}`, data);
            mostrarToast("Producto actualizado", "info");
        } else {
            await axios.post("/api/artista/productos", data);
            mostrarToast("Producto creado", "success");
        }

        productoModal.hide();
        setTimeout(() => location.reload(), 1000);
    } catch {
        mostrarToast("Error al guardar", "error");
    }
});

/* Editar */
document.getElementById("productosTable").addEventListener("click", e => {
    if (!e.target.closest(".btnEditar")) return;
    const tr = e.target.closest("tr");

    axios.get(`/api/artista/productos/${tr.dataset.id}`)
        .then(res => abrirModal(res.data))
        .catch(() => mostrarToast("Error al cargar datos", "error"));
});

/* Eliminar */
document.getElementById("productosTable").addEventListener("click", e => {
    if (!e.target.closest(".btnEliminar")) return;
    const tr = e.target.closest("tr");

    idProductoAEliminar = tr.dataset.id;
    document.getElementById("nombreProductoEliminar").textContent =
        tr.children[1].textContent;

    eliminarModal.show();
});

document.getElementById("btnConfirmarEliminar").addEventListener("click", () => {
    axios.delete(`/api/artista/productos/${idProductoAEliminar}`)
        .then(() => {
            mostrarToast("Producto eliminado", "error");
            eliminarModal.hide();
            setTimeout(() => location.reload(), 1100);
        })
        .catch(() => mostrarToast("Error al eliminar", "error"));
});

/* =======================================================
   FILTRADO + ORDENAMIENTO
======================================================= */
let sortDirection = {};

const searchTitulo = document.getElementById('searchTitulo');
const searchCategoria = document.getElementById('searchCategoria');
const tableBody = document.getElementById('productosTable');

function actualizarTabla() {
    const rows = Array.from(tableBody.querySelectorAll('tr')).filter(r => r.style.display !== 'none');

    // Actualizar contador ID autoincrementable
    rows.forEach((row, idx) => {
        row.children[0].textContent = idx + 1;
    });
}

function filtrarYOrdenar() {
    const titulo = searchTitulo.value.toLowerCase();
    const categoria = searchCategoria.value.toLowerCase();

    const rows = Array.from(tableBody.querySelectorAll('tr'));

    rows.forEach(row => {
        const rowTitulo = row.children[1].textContent.toLowerCase();
        const rowCategoria = row.children[2].textContent.toLowerCase();

        if(rowTitulo.includes(titulo) && (categoria === "" || rowCategoria === categoria)) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    });

    actualizarTabla();
}

searchTitulo.addEventListener('input', filtrarYOrdenar);
searchCategoria.addEventListener('change', filtrarYOrdenar);

/* Ordenar columnas excepto ID */
function sortTable(th) {
    const column = th.getAttribute('data-column');
    if(column === 'id') return; // ID no es ordenable

    const rows = Array.from(tableBody.querySelectorAll('tr')).filter(r => r.style.display !== 'none');
    const asc = !sortDirection[column];
    sortDirection[column] = asc;

    rows.sort((a, b) => {
        let aVal, bVal;
        switch(column) {
            case 'titulo':
                aVal = a.children[1].textContent.toLowerCase();
                bVal = b.children[1].textContent.toLowerCase();
                break;
            case 'categoria':
                aVal = a.children[2].textContent.toLowerCase();
                bVal = b.children[2].textContent.toLowerCase();
                break;
            case 'precio':
                aVal = parseFloat(a.children[3].textContent.replace('S/',''));
                bVal = parseFloat(b.children[3].textContent.replace('S/',''));
                break;
            case 'stock':
                aVal = parseInt(a.children[4].textContent.replace(/\D/g,''));
                bVal = parseInt(b.children[4].textContent.replace(/\D/g,''));
                break;
        }

        if(aVal < bVal) return asc ? -1 : 1;
        if(aVal > bVal) return asc ? 1 : -1;
        return 0;
    });

    rows.forEach(row => tableBody.appendChild(row));
    actualizarTabla();
}
