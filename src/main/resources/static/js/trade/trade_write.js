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

function setSelectedOption() {
  const customSelect = document.querySelectorAll(".custom-select")[0];
  if (customSelect === null || customSelect === undefined) return;

  const selectEl = customSelect.querySelector("select");
  const selectedText = selectEl.options[selectEl.selectedIndex].textContent;
  const selectItems = document.querySelector(".select-items");

  selectItems.querySelectorAll("div").forEach((el) => {
    if (el.textContent === selectedText) {
      el.classList.add("same-as-selected");
      return;
    }
  });
}

/*
 * Todo : 이미지 용량 제한 알림창 표시(에러는 나중에 이동하도록 설정)
 */
function beforeSubmit(event) {
  const input = document.getElementById("images");
  const files = Array.from(input.files);
  const MAX_COUNT = 5;
  const MAX_TOTAL_BYTES = 100 * 1024 * 1024; // 100MB

  if (files.length > MAX_COUNT) {
    alert(`파일은 최대 ${MAX_COUNT}개까지 첨부할 수 있습니다.`);
    event.preventDefault();
    return;
  }

  files.forEach((file) => {
    if (file.size > 100 * 1024 * 1024) {
      alert("각 파일의 용량은 100MB를 초과할 수 없습니다.");
      event.preventDefault();
      return;
    }
  });

  const totalBytes = files.reduce((sum, f) => sum + f.size, 0);
  if (totalBytes > MAX_TOTAL_BYTES) {
    alert("첨부파일 전체 용량은 100MB를 초과할 수 없습니다.");
    event.preventDefault();
    return;
  }
}

document.addEventListener("DOMContentLoaded", () => {
  selectTradeType();
  setSelectedOption();
  document.querySelector("form").addEventListener("submit", beforeSubmit);
});
