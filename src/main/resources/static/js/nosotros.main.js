// mantiene la funcionalidad ahora respetando tu CSS/paleta
document.addEventListener('DOMContentLoaded', () => {
  const cards = document.querySelectorAll('.info-card');
  const MOBILE_BREAKPOINT = 768;

  // IntersectionObserver para reveal
  const io = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('in-view');
        io.unobserve(entry.target);
      }
    });
  }, { threshold: 0.12 });

  cards.forEach(c => io.observe(c));

  // mobile collapsible
  function enableMobileCollapsible(enable) {
    cards.forEach(card => {
      const answer = card.querySelector('.answer');
      const toggle = card.querySelector('.card-toggle');
      const question = card.querySelector('.question');

      if (!answer) return;

      // remove previous listeners to avoid duplicates
      question.removeEventListener('click', handleToggle);
      if (toggle) toggle.removeEventListener('click', handleToggle);
      card.removeEventListener('keydown', handleKey);

      if (enable) {
        // start collapsed
        answer.classList.add('collapsed');
        answer.style.maxHeight = '0px';
        if (toggle) toggle.setAttribute('aria-expanded', 'false');

        question.style.cursor = 'pointer';
        question.addEventListener('click', handleToggle);
        if (toggle) toggle.addEventListener('click', handleToggle);
        card.addEventListener('keydown', handleKey);
      } else {
        // expanded (desktop)
        answer.classList.remove('collapsed');
        answer.style.maxHeight = null;
        if (toggle) toggle.setAttribute('aria-expanded', 'true');
        question.style.cursor = 'default';
      }
    });
  }

  function handleToggle(e) {
    // soporta clicks en question o en el botón toggle
    const card = e.currentTarget.closest('.info-card') || this.closest('.info-card');
    const answer = card.querySelector('.answer');
    const toggle = card.querySelector('.card-toggle');
    if (!answer) return;

    const nowCollapsed = !answer.classList.toggle('collapsed');

    // update inline maxHeight para animación suave
    if (!answer.classList.contains('collapsed')) {
      answer.style.maxHeight = answer.scrollHeight + 'px';
      answer.style.opacity = 1;
      if (toggle) toggle.setAttribute('aria-expanded', 'true');
      const icon = toggle ? toggle.querySelector('i') : null;
      if (icon) icon.style.transform = 'rotate(180deg)';
    } else {
      answer.style.maxHeight = '0px';
      answer.style.opacity = 0;
      if (toggle) toggle.setAttribute('aria-expanded', 'false');
      const icon = toggle ? toggle.querySelector('i') : null;
      if (icon) icon.style.transform = 'rotate(0deg)';
    }
  }

  function handleKey(e) {
    if (e.key === 'Enter' || e.key === ' ') {
      const focused = document.activeElement;
      if (focused && focused.closest('.info-card')) {
        e.preventDefault();
        const q = focused.querySelector('.question') || focused;
        if (q) q.click();
      }
    }
  }

  // init and on resize (debounced)
  function init() {
    const isMobile = window.innerWidth < MOBILE_BREAKPOINT;
    enableMobileCollapsible(isMobile);
    if (isMobile) {
      // ensure collapsed answers have height 0
      document.querySelectorAll('.answer.collapsed').forEach(a => {
        a.style.maxHeight = '0px';
        a.style.opacity = 0;
      });
    } else {
      document.querySelectorAll('.answer').forEach(a => {
        a.style.maxHeight = null;
        a.style.opacity = 1;
      });
    }
  }
  init();

  let resizeTimer = null;
  window.addEventListener('resize', () => {
    clearTimeout(resizeTimer);
    resizeTimer = setTimeout(init, 160);
  });

  // smooth scroll for any anchor links inside the page
  document.querySelectorAll('a[href^="#"]').forEach(a => {
    a.addEventListener('click', (e) => {
      const target = document.querySelector(a.getAttribute('href'));
      if (target) {
        e.preventDefault();
        window.scrollTo({
          top: target.getBoundingClientRect().top + window.scrollY - 80,
          behavior: 'smooth'
        });
      }
    });
  });

});
