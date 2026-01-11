// Carrusel automático cada 5 segundos
document.addEventListener('DOMContentLoaded', function() {
    const carousel = new bootstrap.Carousel(document.getElementById('heroCarousel'), {
        interval: 5000, // 5 segundos
        ride: 'carousel'
    });
});

// Navbar scrolled: Agregar clase 'scrolled' al bajar más de 50px
window.addEventListener('scroll', function() {
    const navbar = document.getElementById('navbar');
    if (window.scrollY > 50) {
        navbar.classList.add('scrolled');
    } else {
        navbar.classList.remove('scrolled');
    }
});

// Scroll reveal: Animar elementos al aparecer en pantalla
function revealOnScroll() {
    const reveals = document.querySelectorAll('.reveal');
    reveals.forEach(element => {
        const elementTop = element.getBoundingClientRect().top;
        const windowHeight = window.innerHeight;
        if (elementTop < windowHeight - 100) {
            element.classList.add('fade-in');
        }
    });
}

window.addEventListener('scroll', revealOnScroll);
revealOnScroll(); // Ejecutar al cargar la página

// Animar botones al hacer hover (aumento de tamaño y brillo)
document.querySelectorAll('.btn').forEach(btn => {
    btn.addEventListener('mouseenter', function() {
        this.style.transform = 'scale(1.05)';
        this.style.boxShadow = '0 4px 15px rgba(0, 0, 0, 0.2)';
    });
    btn.addEventListener('mouseleave', function() {
        this.style.transform = 'scale(1)';
        this.style.boxShadow = 'none';
    });
});

// Suave desplazamiento para enlaces ancla (ya incluido en CSS con scroll-behavior: smooth, pero reforzado aquí si es necesario)
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function(e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});