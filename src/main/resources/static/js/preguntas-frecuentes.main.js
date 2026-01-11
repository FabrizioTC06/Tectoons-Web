document.addEventListener("DOMContentLoaded", function () {
  // Smooth scroll para anchors internos (si agregas navegación con #id)
  document.querySelectorAll('a[href^="#"]').forEach(a => {
    a.addEventListener('click', function (e) {
      const target = document.querySelector(this.getAttribute('href'));
      if (target) {
        e.preventDefault();
        window.scrollTo({
          top: target.offsetTop - 80,
          behavior: 'smooth'
        });
      }
    });
  });

  // Si usas una nav de secciones (como en T&C), puedes activar la sección visible:
  const sections = document.querySelectorAll('.terms-section, .faq-section, .accordion-item');
  const navLinks = document.querySelectorAll('.nav-sidebar .nav-link');

  function onScrollHighlight() {
    let current = '';
    sections.forEach(sec => {
      const top = sec.getBoundingClientRect().top;
      if (top <= 120) {
        current = sec.id || current;
      }
    });
    if (navLinks.length && current) {
      navLinks.forEach(n => {
        n.classList.toggle('active', n.getAttribute('href') === `#${current}`);
      });
    }
  }

  window.addEventListener('scroll', onScrollHighlight, { passive: true });

  // Accesibilidad: dejar el primer item del accordion con foco para keyboard users (opcional)
  const firstButton = document.querySelector('#faqAccordion .accordion-button');
  if (firstButton) {
    firstButton.setAttribute('aria-expanded', 'true');
  }
});
