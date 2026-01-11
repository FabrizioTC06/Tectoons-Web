
document.addEventListener('DOMContentLoaded', function () {
    const navLinks = document.querySelectorAll('.nav-sidebar .nav-link');
    const sections = document.querySelectorAll('.terms-section');

    navLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();

            const targetId = this.getAttribute('href');
            const targetSection = document.querySelector(targetId);

            if (targetSection) {
                navLinks.forEach(nav => nav.classList.remove('active'));
                this.classList.add('active');
                window.scrollTo({
                    top: targetSection.offsetTop - 100,
                    behavior: 'smooth'
                });
            }
        });
    });

    function highlightActiveSection() {
        let currentSection = '';

        sections.forEach(section => {
            const sectionTop = section.offsetTop - 150;
            const sectionHeight = section.clientHeight;

            if (window.scrollY >= sectionTop && window.scrollY < sectionTop + sectionHeight) {
                currentSection = section.getAttribute('id');
            }
        });

        navLinks.forEach(link => {
            link.classList.remove('active');
            if (link.getAttribute('href') === `#${currentSection}`) {
                link.classList.add('active');
            }
        });
    }

    window.addEventListener('scroll', highlightActiveSection);
});

document.addEventListener('DOMContentLoaded', function () {
    const navLinks = document.querySelectorAll('.nav-sidebar .nav-link');
    const sections = document.querySelectorAll('.terms-section');
    const sidebar = document.getElementById('sidebarMenu');
    const toggleBtn = document.querySelector('.toggle-sidebar');

    // Toggle sidebar en móviles
    toggleBtn.addEventListener('click', function () {
        sidebar.classList.toggle('show');
    });

    // Desplazamiento suave
    navLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();
            const targetId = this.getAttribute('href');
            const targetSection = document.querySelector(targetId);

            if (targetSection) {
                window.scrollTo({
                    top: targetSection.offsetTop - 100,
                    behavior: 'smooth'
                });
                navLinks.forEach(nav => nav.classList.remove('active'));
                this.classList.add('active');
                // Ocultar menú al hacer clic (solo móvil)
                if (window.innerWidth < 992) {
                    sidebar.classList.remove('show');
                }
            }
        });
    });

    // Resaltar sección activa al hacer scroll
    function highlightActiveSection() {
        let currentSection = '';
        sections.forEach(section => {
            const sectionTop = section.offsetTop - 150;
            const sectionHeight = section.clientHeight;
            if (window.scrollY >= sectionTop && window.scrollY < sectionTop + sectionHeight) {
                currentSection = section.getAttribute('id');
            }
        });
        navLinks.forEach(link => {
            link.classList.remove('active');
            if (link.getAttribute('href') === `#${currentSection}`) {
                link.classList.add('active');
            }
        });
    }

    window.addEventListener('scroll', highlightActiveSection);
});
