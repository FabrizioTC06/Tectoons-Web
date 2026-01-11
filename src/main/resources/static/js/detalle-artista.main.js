/**
 * detalle-artista.main.js
 * Funcionalidades dinámicas:
 * - Carga de secciones al hacer clic (lazy load simulada)
 * - Botón Seguir con localStorage
 * - Lightbox para galería
 * - Animaciones reveal con IntersectionObserver
 */
/*
document.addEventListener('DOMContentLoaded', () => {
  const tabs = document.querySelectorAll('#artistTabs a.nav-link');
  const tabContent = document.getElementById('tabContent');

  // 🧡 FOLLOW localStorage
  const btnFollow = document.getElementById('btnFollow');
  const FOLLOW_KEY = 'tectoons_follows';
  if (btnFollow) {
    const id = btnFollow.dataset.idTh || btnFollow.getAttribute('data-id-th');
    const follows = new Set(JSON.parse(localStorage.getItem(FOLLOW_KEY) || '[]'));
    const update = () => {
      const active = follows.has(id);
      btnFollow.classList.toggle('btn-success', active);
      btnFollow.innerHTML = active ? '<i class="bi bi-heart-fill"></i> Siguiendo' : '<i class="bi bi-heart"></i> Seguir';
      btnFollow.setAttribute('aria-pressed', active);
    };
    update();
    btnFollow.addEventListener('click', () => {
      follows.has(id) ? follows.delete(id) : follows.add(id);
      localStorage.setItem(FOLLOW_KEY, JSON.stringify([...follows]));
      update();
    });
  }

  // 🎨 Tabs dinámicas
  tabs.forEach(tab => {
    tab.addEventListener('click', async (e) => {
      e.preventDefault();
      const targetId = tab.getAttribute('href').substring(1);
      tabs.forEach(t => t.classList.remove('active'));
      tab.classList.add('active');
      const target = document.getElementById(targetId);
      if (target) {
        // Si aún no cargó, simulamos fetch
        if (!target.dataset.loaded) {
          target.innerHTML = `<div class="text-center py-5 text-muted"><div class="spinner-border text-primary"></div><p>Cargando ${targetId}...</p></div>`;
          await new Promise(res => setTimeout(res, 600)); // Simular delay
          target.innerHTML = renderSection(targetId);
          target.dataset.loaded = "true";
          revealAnimate();
        }
        document.querySelectorAll('.tab-pane').forEach(p => p.classList.remove('show', 'active'));
        target.classList.add('show', 'active');
        window.scrollTo({ top: target.offsetTop - 60, behavior: 'smooth' });
      }
    });
  });

  // 🖼 Lightbox
  document.body.addEventListener('click', (e) => {
    const thumb = e.target.closest('.gallery-thumb');
    if (!thumb) return;
    const overlay = document.createElement('div');
    overlay.className = 'lightbox-backdrop';
    overlay.innerHTML = `<div class="lightbox-content"><img src="${thumb.src}" alt=""></div>`;
    overlay.addEventListener('click', () => overlay.remove());
    document.body.appendChild(overlay);
  });

  // 🌟 Intersection reveal
  function revealAnimate() {
    const obs = new IntersectionObserver((entries) => {
      entries.forEach(e => {
        if (e.isIntersecting) {
          e.target.classList.add('fade-in-up');
          obs.unobserve(e.target);
        }
      });
    }, { threshold: 0.1 });
    document.querySelectorAll('section').forEach(sec => obs.observe(sec));
  }
  revealAnimate();

  // 🧩 Plantillas
  function renderSection(id) {
    switch (id) {
      case 'galeria':
        return `
        <div class="row g-3" id="galleryGrid">
          <div class="col-md-4"><div class="card"><img src="https://picsum.photos/seed/art1/800/600" class="gallery-thumb" alt="obra" loading="lazy"><div class="card-body"><h6>Obra #1</h6></div></div></div>
          <div class="col-md-4"><div class="card"><img src="https://picsum.photos/seed/art2/800/600" class="gallery-thumb" alt="obra" loading="lazy"><div class="card-body"><h6>Obra #2</h6></div></div></div>
          <div class="col-md-4"><div class="card"><img src="https://picsum.photos/seed/art3/800/600" class="gallery-thumb" alt="obra" loading="lazy"><div class="card-body"><h6>Obra #3</h6></div></div></div>
        </div>`;
      case 'about':
        return `<h3>Sobre el artista</h3><p>Artista peruano apasionado por el arte digital y la animación. Inspirado en mundos fantásticos y el color.</p>`;
      case 'posts':
        return `<h3>Posts</h3><article class="card p-3 mb-3"><h5>Nuevo proyecto en progreso</h5><p class="text-muted">Publicado el 20/10/2025</p><p>Explorando nuevas técnicas de ilustración 3D.</p></article>`;
      case 'shop':
        return `<h3>Tienda</h3><div class="row g-3"><div class="col-md-4"><div class="card"><img src="https://picsum.photos/seed/shopx/800/600" alt="producto" class="img-fluid"><div class="card-body"><h6>Print edición limitada</h6><p>S/ 69.90</p></div></div></div></div>`;
      default:
        return `<p>Sección no encontrada.</p>`;
    }
  }
});
*/

document.addEventListener("DOMContentLoaded", () => {
    console.log("👍 Sistema de Likes cargado");

    const likeButtons = document.querySelectorAll(".btn-like");

    likeButtons.forEach(btn => {
        const obraId = btn.dataset.obraId;
        const icon = btn.querySelector(".icon-like");
        const countSpan = btn.querySelector(".like-count");

        // 1. Estado inicial: Like o no
        fetch(`/api/likes/exists/${obraId}`)
            .then(res => res.json())
            .then(data => {
                if (data.exists && data.logged) {
                    btn.classList.add("liked");
                    icon.classList.replace("bi-heart", "bi-heart-fill");
                }
            });

        // 2. Cargar número de likes
        fetch(`/api/likes/count/${obraId}`)
            .then(res => res.json())
            .then(data => {
                countSpan.textContent = data.totalLikes;
            });

        // 3. Al hacer click → toggle like
        btn.addEventListener("click", () => {

    fetch(`/api/likes/${obraId}`, {
        method: "POST"
    })
        .then(async res => {

            if (res.status === 401) {
                const modal = new bootstrap.Modal(document.getElementById("loginRequiredModal"));
                modal.show();
                return;
            }

            const data = await res.json();

            if (data.logged === false) {
                const modal = new bootstrap.Modal(document.getElementById("loginRequiredModal"));
                modal.show();
                return;
            }

            if (data.liked) {
                btn.classList.add("liked");
                icon.classList.replace("bi-heart", "bi-heart-fill");
            } else {
                btn.classList.remove("liked");
                icon.classList.replace("bi-heart-fill", "bi-heart");
            }

            countSpan.textContent = data.totalLikes;
        })
        .catch(err => console.error("Error en el Like:", err));
});

    });
});
document.addEventListener("DOMContentLoaded", () => {

    const followBtn = document.getElementById("btnFollow");

    if (followBtn) {
        const artistaId = followBtn.getAttribute("data-artista-id");
        const isLogged = followBtn.getAttribute("data-logged") === "true";

        followBtn.addEventListener("click", async () => {

            if (!isLogged) {
                const modal = new bootstrap.Modal(document.getElementById("loginRequiredModal"));
                modal.show();
                return;
            }

            try {
                const res = await fetch(`/api/seguidores/toggle/${artistaId}`, {
                    method: "POST"
                });

                const data = await res.json();

                if (!data.ok) return;

                // Cambiar estilo del botón
                if (data.siguiendo) {
                    followBtn.classList.remove("btn-primary");
                    followBtn.classList.add("btn-success");
                    followBtn.textContent = "Siguiendo";
                } else {
                    followBtn.classList.remove("btn-success");
                    followBtn.classList.add("btn-primary");
                    followBtn.textContent = "Seguir";
                }

                // 🔥 ACTUALIZAR CONTADOR EN TIEMPO REAL
                const countEl = document.querySelector("#followersCount span");
                if (countEl) countEl.textContent = data.totalSeguidores;

            } catch (e) {
                console.error("Error en follow:", e);
            }
        });
    }

});


// Bootstrap Alert bonita
function mostrarAlerta(msg) {
    const alertBox = document.createElement("div");
    alertBox.className =
        "alert alert-warning alert-dismissible fade show position-fixed top-0 end-0 m-4 shadow-lg";
    alertBox.style.zIndex = "2000";
    alertBox.innerHTML = `
        <strong>⚠ Atención:</strong> ${msg}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    document.body.appendChild(alertBox);

    setTimeout(() => {
        alertBox.classList.remove("show");
        alertBox.addEventListener("transitionend", () => alertBox.remove());
    }, 4000);
}
