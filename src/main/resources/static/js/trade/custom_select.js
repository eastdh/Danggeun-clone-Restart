export function customSelect() {
  document.addEventListener("DOMContentLoaded", function () {
    const customSelects = document.querySelectorAll(".custom-select");

    customSelects.forEach((container) => {
      const selectEl = container.querySelector("select");
      const selected = document.createElement("DIV");
      selected.className = "select-selected";
      selected.textContent =
        selectEl.options[selectEl.selectedIndex]?.textContent || "선택";
      container.appendChild(selected);

      const optionsDiv = document.createElement("DIV");
      optionsDiv.className = "select-items select-hide";
      Array.from(selectEl.options)
        .slice(1)
        .forEach((option) => {
          const div = document.createElement("DIV");
          div.textContent = option.textContent;
          div.addEventListener("click", function () {
            selectEl.value = option.value;
            selected.textContent = this.textContent;
            optionsDiv
              .querySelectorAll(".same-as-selected")
              .forEach((el) => el.classList.remove("same-as-selected"));
            this.classList.add("same-as-selected");
            selected.click();
          });
          optionsDiv.appendChild(div);
        });
      container.appendChild(optionsDiv);

      selected.addEventListener("click", function (e) {
        e.stopPropagation();
        closeAllSelects(selected);
        optionsDiv.classList.toggle("select-hide");
        selected.classList.toggle("select-arrow-active");
      });
    });

    document.addEventListener("click", () => closeAllSelects());

    function closeAllSelects(except) {
      document.querySelectorAll(".select-items").forEach((el) => {
        if (except?.nextSibling !== el) el.classList.add("select-hide");
      });
      document.querySelectorAll(".select-selected").forEach((el) => {
        if (el !== except) el.classList.remove("select-arrow-active");
      });
    }
  });
}
