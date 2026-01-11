/* TecToons - Portafolio JS
   Controla filtros, búsqueda, hover videos, modal y animaciones de aparición.
*/

document.addEventListener("DOMContentLoaded", () => {
    const grid = document.getElementById("portfolio-grid");
    const searchInput = document.getElementById("searchInput");
    const filterButtons = document.querySelectorAll(".btn-group button");
    const modal = document.getElementById("obraModal");
    const prevBtn = document.getElementById("prevPage");
    const nextBtn = document.getElementById("nextPage");
    const pageIndicator = document.getElementById("pageIndicator");

    let currentCategory = "all";
    let currentPage = 1;
    const itemsPerPage = 9;

    const items = Array.from(grid.querySelectorAll(".portfolio-item"));

    // ---- Filtros ----
    filterButtons.forEach(btn => {
        btn.addEventListener("click", () => {
            filterButtons.forEach(b => b.classList.remove("active"));
            btn.classList.add("active");
            currentCategory = btn.dataset.category;
            currentPage = 1;
            applyFilters();
        });
    });

    // ---- Búsqueda ----
    searchInput.addEventListener("input", () => {
        currentPage = 1;
        applyFilters();
    });

    // ---- Paginación ----
    prevBtn.addEventListener("click", () => {
        if (currentPage > 1) {
            currentPage--;
            applyFilters();
        }
    });

    nextBtn.addEventListener("click", () => {
        const visible = getFilteredItems();
        const maxPages = Math.ceil(visible.length / itemsPerPage);
        if (currentPage < maxPages) {
            currentPage++;
            applyFilters();
        }
    });

    function getFilteredItems() {
        const term = searchInput.value.toLowerCase();
        return items.filter(item => {
            const matchesCategory = currentCategory === "all" || item.dataset.category === currentCategory;
            const matchesSearch = item.textContent.toLowerCase().includes(term);
            return matchesCategory && matchesSearch;
        });
    }

    function applyFilters() {
        const visible = getFilteredItems();
        const maxPages = Math.ceil(visible.length / itemsPerPage);
        items.forEach(el => el.classList.add("d-none"));
        visible
            .slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage)
            .forEach(el => el.classList.remove("d-none"));
        pageIndicator.textContent = `${currentPage} / ${maxPages || 1}`;
    }

    applyFilters();

    // ---- Hover play/pause videos ----
    grid.querySelectorAll("video").forEach(video => {
        video.addEventListener("mouseenter", () => video.play());
        video.addEventListener("mouseleave", () => {
            video.pause();
            video.currentTime = 0;
        });
    });

    // ---- Modal ----
    modal.addEventListener("show.bs.modal", e => {
        const trigger = e.relatedTarget;
        const title = trigger.getAttribute("data-title");
        const img = trigger.getAttribute("data-img");
        const video = trigger.getAttribute("data-video");
        const desc = trigger.getAttribute("data-descripcion");
        const author = trigger.getAttribute("data-autor");

        const modalTitle = modal.querySelector("#modalTitle");
        const modalAuthor = modal.querySelector("#modalAuthor");
        const modalDescription = modal.querySelector("#modalDescription");
        const modalImage = modal.querySelector("#modalImage");
        const modalVideo = modal.querySelector("#modalVideo");

        modalTitle.textContent = title;
        modalAuthor.textContent = author;
        modalDescription.textContent = desc;

        if (video && video.trim() !== "") {
            modalVideo.src = video;
            modalVideo.classList.remove("d-none");
            modalImage.classList.add("d-none");
        } else {
            modalImage.src = img;
            modalImage.classList.remove("d-none");
            modalVideo.classList.add("d-none");
        }
    });

    // ---- Animaciones con IntersectionObserver ----
    const observer = new IntersectionObserver(entries => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add("active");
                observer.unobserve(entry.target);
            }
        });
    }, { threshold: 0.2 });

    document.querySelectorAll(".reveal").forEach(el => observer.observe(el));
});

/*
const likeBtn = document.getElementById('likeButton');
likeBtn.addEventListener('click', () => {
    likeBtn.classList.add('liked');
    likeBtn.querySelector('span').textContent = '¡Te gusta!';
    // Aquí más adelante agregarás lógica de Spring + base de datos
});
*/

// Toggle menú móvil
document.getElementById("toggleNavigation")?.addEventListener("click", () => {
    const sidebar = document.getElementById("sidebarMenu");
    sidebar.classList.toggle("show");
});
