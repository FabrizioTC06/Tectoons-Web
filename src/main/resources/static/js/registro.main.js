document.addEventListener("DOMContentLoaded", () => {
  const dniInput = document.getElementById("dni");

  dniInput.addEventListener("input", () => {
    const value = dniInput.value.trim();
    if (value.length === 8 && !isNaN(value)) {
      dniInput.classList.remove("is-invalid");
      dniInput.classList.add("is-valid");
    } else {
      dniInput.classList.remove("is-valid");
      dniInput.classList.add("is-invalid");
    }
  });
});
