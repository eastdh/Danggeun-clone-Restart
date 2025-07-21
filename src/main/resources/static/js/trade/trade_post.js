import { customSelect } from "./custom_select.js";
customSelect();

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

  if (statusButton && modal && cancelButton && statusForm) {
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
          if (res.ok) {
            alert("거래글 상태가 변경되었습니다.");
            location.reload();
          } else alert("거래글 상태 변경을 실패했습니다.");
        })
        .catch(() => {
          alert("오류로 인해 상태를 변경할 수 없습니다");
        });
    });
  }
}

function addStatusWarning() {
  const customSelect = document.querySelectorAll(".custom-select")[0];
  if (customSelect === null || customSelect === undefined) return;

  const selectEl = customSelect.querySelector("select");
  const selectItems = customSelect.querySelectorAll(".select-items");
  const warning = document.getElementsByClassName("status-warning")[0];

  if (selectEl.options[selectEl.selectedIndex].value === "COMPLETED") {
    warning.classList.add("active");
  }

  selectItems.forEach((el) => {
    el.addEventListener("click", function () {
      const selectedDiv = selectEl.options[selectEl.selectedIndex];
      if (selectedDiv.value === "COMPLETED") {
        warning.classList.add("active");
      } else {
        warning.classList.remove("active");
      }
    });
  });
}

function bumpTrade() {
  const bumpButton = document.getElementById("bump-button");

  if (bumpButton) {
    const tradeId = bumpButton.dataset.tradeId;
    bumpButton.addEventListener("click", function () {
      if (!window.confirm("거래글을 끌올할까요?")) {
        return;
      }

      fetch(`/api/trade/${tradeId}/bump`, {
        method: "POST",
      })
        .then((res) => {
          if (res.ok) location.reload();
          else throw new Error("거래글 끌올 실패");
        })
        .catch(() => {
          alert("오류로 인해 끌올에 실패했습니다");
        });
    });
  }
}

function toggleLike() {
  let pending = false;
  let debounceTimer = null;
  const likeButton = document.getElementById("like-button");

  if (!likeButton) return;

  const likedImageEl = likeButton.querySelector(".like-image.liked");
  const unLikedImageEl = likeButton.querySelector(".like-image.un-liked");
  const tradeId = likeButton.dataset.tradeId;
  let currentLiked = likeButton.dataset.liked === "true";
  let clientSideLiked = currentLiked;
  let requestLiked = currentLiked;

  likeButton.addEventListener("click", function () {
    clientSideLiked = !clientSideLiked;
    updateUI();

    if (pending) {
      clearTimeout(debounceTimer);
    }
    pending = true;
    debounceTimer = setTimeout(() => {
      requestLiked = clientSideLiked;
      sendLikeRequest(tradeId, requestLiked);
    }, 1000);
  });

  function updateUI() {
    if (clientSideLiked) {
      likeButton.classList.add("active");
      likedImageEl.classList.remove("hidden");
      unLikedImageEl.classList.add("hidden");
    } else {
      likeButton.classList.remove("active");
      likedImageEl.classList.add("hidden");
      unLikedImageEl.classList.remove("hidden");
    }
  }

  async function sendLikeRequest(tradeId, requestLiked) {
    pending = false;
    const url = requestLiked
      ? `/api/trade/${tradeId}/like`
      : `/api/trade/${tradeId}/remove-like`;
    if (currentLiked === requestLiked) return;
    try {
      await fetch(url, {
        method: "POST",
      })
        .then((res) => {
          if (res.ok) return res.json();
          else
            throw new Error(
              requestLiked ? "좋아요 등록 실패" : "좋아요 제거 실패"
            );
        })
        .then((data) => {
          currentLiked = data.liked;
        });
    } catch (e) {
      clientSideLiked = !clientSideLiked;
      updateUI();
      alert("좋아요 등록/제거 동작에 실패했습니다");
    }
  }

  window.addEventListener("beforeunload", () => {
    requestLiked = clientSideLiked;
    if (pending) {
      navigator.sendBeacon(
        requestLiked
          ? `/api/trade/${tradeId}/like`
          : `/api/trade/${tradeId}/remove-like`
      );
    }
  });
}

document.addEventListener("DOMContentLoaded", () => {
  addCarouselListeners();
  addLinks();
  updateMannerScore();
  setStatusModal();
  addStatusWarning();
  bumpTrade();
  toggleLike();
});
