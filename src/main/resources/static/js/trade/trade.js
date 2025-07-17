function makeStatusFilterLink() {
  const params = new URLSearchParams(window.location.search);
  const url = new URL(window.location);

  const tradeAbleLink = document.getElementsByClassName("filter-trade-able")[0];
  const tradeAbleCheckBox = document.getElementsByClassName("check-img-box")[0];

  if (!params.has("status")) {
    tradeAbleCheckBox.classList.remove("active");
    params.set("status", "available");
  } else {
    if (params.get("status") === "available") {
      tradeAbleCheckBox.classList.add("active");
      params.delete("status");
    }
  }
  url.search = params.toString();
  tradeAbleLink.href = url.toString();
}

function makeCategoryFilterLink() {
  const params = new URLSearchParams(window.location.search);
  const url = new URL(window.location);

  const categoryLink = document.getElementsByClassName("filter-link category");

  Array.from(categoryLink).forEach((link) => {
    const radio = link.querySelector(".radio-img-box");
    if (!params.has("category")) {
      radio.classList.remove("active");
    } else {
      if (params.get("category") === radio.dataset.category)
        radio.classList.add("active");
    }
  });

  Array.from(categoryLink).forEach((link) => {
    const radio = link.querySelector(".radio-img-box");
    params.set("category", radio.dataset.category);
    url.search = params.toString();
    link.href = url.toString();
  });
}

function makePriceFilterLink() {
  const params = new URLSearchParams(window.location.search);
  const url = new URL(window.location);

  const priceLink = document.getElementsByClassName("filter-link-price");
  const priceInputStart = document.getElementsByClassName("price-start")[0];
  const priceInputEnd = document.getElementsByClassName("price-end")[0];

  if (!params.has("price")) {
    Array.from(priceLink).forEach((link) => {
      link.classList.remove("active");
    });
  } else {
    const priceStart = params.get("price").split("_")[0];
    const priceEnd = params.get("price").split("_")[1];

    if (priceStart === "0") {
      if (priceEnd === "0") {
        priceLink[0].classList.add("active");
      } else if (priceEnd === "5000") {
        priceLink[1].classList.add("active");
      } else if (priceEnd === "10000") {
        priceLink[2].classList.add("active");
      } else if (priceEnd === "20000") {
        priceLink[3].classList.add("active");
      }
    }

    priceInputStart.value = priceStart;
    priceInputEnd.value = priceEnd;
  }

  Array.from(priceLink).forEach((link) => {
    params.set("price", "0_" + link.dataset.price);
    url.search = params.toString();
    link.href = url.toString();
  });
}

function makePriceRangeFilterButton() {
  const priceButton = document.getElementsByClassName("filter-price-button")[0];
  priceButton.addEventListener("click", () => {
    const params = new URLSearchParams(window.location.search);
    const url = new URL(window.location);

    const priceInputStart =
      document.getElementsByClassName("price-start")[0].value;
    const priceInputEnd = document.getElementsByClassName("price-end")[0].value;

    params.set("price", priceInputStart + "_" + priceInputEnd);
    url.search = params.toString();
    window.location.href = url.toString();
  });
}

function makeFilterRemoveButton() {
  const removeButtons = document.getElementsByClassName("filter-remove-button");
  Array.from(removeButtons).forEach((button) => {
    button.addEventListener("click", () => {
      const params = new URLSearchParams(window.location.search);
      const url = new URL(window.location);

      params.delete(button.dataset.filter);
      url.search = params.toString();
      window.location.href = url.toString();
    });
  });
}

makeStatusFilterLink();
makeCategoryFilterLink();
makePriceFilterLink();
makePriceRangeFilterButton();
makeFilterRemoveButton();
