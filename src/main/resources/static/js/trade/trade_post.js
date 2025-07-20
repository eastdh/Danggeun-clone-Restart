let currentIndex = 0;

// carousel
function updateCarousel() {
  const track = document.getElementById("carousel-track");
  const items = document.querySelectorAll(".carousel-item");
  const indicators = document.querySelectorAll(".carousel-indicators button");

  track.style.transform = `translateX(-${currentIndex * 100}%)`;

  indicators.forEach((btn, idx) => {
    btn.classList.toggle("active", idx === currentIndex);
  });
}

function goToSlide(button) {
  currentIndex = parseInt(button.dataset.index);
  updateCarousel();
}

function prevSlide() {
  const total = document.querySelectorAll(".carousel-item").length;
  currentIndex = (currentIndex - 1 + total) % total;
  updateCarousel();
}

function nextSlide() {
  const total = document.querySelectorAll(".carousel-item").length;
  currentIndex = (currentIndex + 1) % total;
  updateCarousel();
}

function addCarouselListeners() {
  const indicatorButtons = document.querySelectorAll(".indicator-buttons");
  const prevButton = document.getElementsByClassName("carousel-button prev")[0];
  const nextButton = document.getElementsByClassName("carousel-button next")[0];

  if (indicatorButtons.length !== 0) {
    indicatorButtons.forEach((button) => {
      button.addEventListener("click", () => {
        goToSlide(button);
      });
    });
  }

  if (prevButton && nextButton) {
    prevButton.addEventListener("click", prevSlide);
    nextButton.addEventListener("click", nextSlide);
  }
}

// tooltip box
document
  .getElementsByClassName("manner-label")[0]
  .addEventListener("click", function (event) {
    const tooltip = event.currentTarget.nextElementSibling;
    tooltip.classList.toggle("active");
  });

document.addEventListener("click", function (e) {
  document.querySelectorAll(".manner-tooltip.active").forEach((tt) => {
    const label = tt.previousElementSibling;
    if (!label.contains(e.target) && !tt.contains(e.target)) {
      tt.classList.remove("active");
    }
  });
});

// link update
function updateLinks(queries, target) {
  const url = new URL(window.location);
  const params = new URLSearchParams(window.location.search);
  url.pathname = "/trade";

  queries.forEach(([queryType, queryValue]) => {
    if (queryValue != null && !params.has(queryType)) {
      params.set(queryType, queryValue);
    }
  });

  url.search = params.toString();
  target.href = url.toString();
}

function addLinks() {
  const locationSearchLink =
    document.getElementsByClassName("writer-location")[0];
  const categorySearchLink =
    document.getElementsByClassName("category-link")[0];
  const keywordSearchLink =
    document.getElementsByClassName("related-trade-link")[0];

  updateLinks(
    [["location", locationSearchLink.dataset.location]],
    locationSearchLink
  );
  updateLinks(
    [["category", categorySearchLink.dataset.category]],
    categorySearchLink
  );
  updateLinks(
    [
      ["keyword", keywordSearchLink.dataset.keyword],
      ["status", keywordSearchLink.dataset.filter],
    ],
    keywordSearchLink
  );
}

// manner score
function updateMannerScore() {
  const filledBar = document.getElementsByClassName("filled-bar")[0];
  const mannerScore = filledBar.dataset.mannerScore;

  filledBar.style.width = mannerScore + "%";
}

// status modal
function setStatusModal() {
  const statusButton = document.getElementById("status-button");
  const modal = document.getElementById("status-alter-modal");
  const cancelButton = document.getElementById("cancel-button");
  const statusForm = document.getElementById("trade-status-form");

  statusButton.addEventListener("click", function (e) {
    e.preventDefault();
    modal.classList.remove("hidden");
  });

  cancelButton.addEventListener("click", function () {
    modal.classList.add("hidden");
  });

  statusForm.addEventListener("submit", function (e) {
    e.preventDefault();
    const statusValue = document.getElementById("status").value;
    const tradeId = document.getElementById("status").dataset.tradeId;

    fetch(`/api/trade/${tradeId}/status`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ status: statusValue }),
    })
      .then((res) => {
        if (res.ok) return res.text();
        else alert("거래글 상태 변경을 실패했습니다.");
      })
      .then(() => {
        alert("거래글 상태가 변경되었습니다.");
        location.reload();
      })
      .catch(() => {
        alert("오류로 인해 상태를 변경할 수 없습니다");
      });
  });
}

document.addEventListener("DOMContentLoaded", () => {
  addCarouselListeners();
  addLinks();
  updateMannerScore();
  setStatusModal();
});
