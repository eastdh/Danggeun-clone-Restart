import { customSelect } from "./custom_select.js";
customSelect();

function selectTradeType() {
  const tradeSelection = document.getElementsByClassName("trade-selection")[0];
  const tradeTypes = tradeSelection.querySelectorAll(".trade-type");
  const priceInput = document.getElementById("price");

  tradeTypes.forEach((type) => {
    type.addEventListener("click", () => {
      if (type.dataset.trade === "forSale") {
        priceInput.classList.remove("inactive");
        priceInput.removeAttribute("readonly");
      } else if (type.dataset.trade === "giveAway") {
        priceInput.classList.add("inactive");
        priceInput.setAttribute("readonly", true);
        priceInput.value = 0;
      }
      type.classList.add("selected");
      tradeTypes.forEach((el) => {
        if (el !== type) el.classList.remove("selected");
      });
    });
  });

  document.addEventListener("DOMContentLoaded", () => {
    const priceInput = document.getElementById("price");
    const price = priceInput?.dataset.price;
    if (priceInput && price) {
      if (price === "0") {
        tradeTypes[1].classList.add("selected");
      }
    } else {
      tradeTypes[0].classList.add("selected");
    }
  });
}

selectTradeType();
