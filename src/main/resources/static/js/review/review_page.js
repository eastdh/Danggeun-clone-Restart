document.addEventListener("DOMContentLoaded", function () {
  const reviewForm = document.getElementById("reviewForm");

  // #region 별점 처리
  const ratingWrap = document.querySelector(".rating");
  const stars = ratingWrap.querySelectorAll(".star-icon");
  applyCheckedRating();

  ratingWrap.addEventListener("mouseover", (e) => {
    if (e.target.classList.contains("star-icon")) {
      const index = Array.from(stars).indexOf(e.target);
      if (index >= 0) {
        clearStars();
        fillStars(index);
        setOpacity(index, "0.5");
      }
    }
  });

  ratingWrap.addEventListener("mouseout", () => {
    clearStars();
    applyCheckedRating();
    setOpacity(stars.length, "1");
  });

  // #endregion

  // #region 키워드/별점 검증 및 제출
  const keywordInputs = reviewForm.querySelectorAll('input[name="categoryIds"]');

  reviewForm.addEventListener("submit", function (e) {
    // 키워드 선택 수 확인
    const checkedKeywords = Array.from(keywordInputs).filter((input) => input.checked);
    if (checkedKeywords.length < 1 || checkedKeywords.length > 5) {
      e.preventDefault();
      alert("키워드를 최소 1개, 최대 5개까지 선택해주세요.");
      return;
    }

    // 별점 선택 확인
    const selectedRating = document.querySelector('.rating input[type="radio"]:checked');
    if (!selectedRating) {
      e.preventDefault();
      alert("별점을 선택해주세요.");
      return;
    }

    console.log("폼 제출됨. 별점:", selectedRating.value, "키워드 수:", checkedKeywords.length);
    // preventDefault 제거 → 정상 제출됨
  });

  // 5개 초과 선택 방지
  keywordInputs.forEach((checkbox) => {
    checkbox.addEventListener("change", () => {
      const checkedCount = Array.from(keywordInputs).filter((input) => input.checked).length;
      if (checkedCount > 5) {
        checkbox.checked = false;
        alert("최대 5개까지만 선택 가능합니다.");
      }
    });
  });

  // #endregion

  // #region 리뷰 가이드 표시
  fetch("/data/review-guides.json")
      .then((response) => response.json())
      .then((guides) => {
        const reviewTarget = "seller"; // TODO: 서버에서 전달받기

        const guideList = reviewTarget === "buyer" ? guides.buyer : guides.seller;
        const kindnessList = guides.kindness;

        const selectedGuide = guideList[Math.floor(Math.random() * guideList.length)];
        const kindnessGuide = kindnessList[Math.floor(Math.random() * kindnessList.length)];

        const guideContainer = document.getElementById("reviewGuide");
        guideContainer.innerHTML = `
        <p class="guide-bubble">${selectedGuide}</p>
        <p class="guide-bubble">${kindnessGuide}</p>
      `;
      })
      .catch((err) => {
        document.getElementById("reviewGuide").innerHTML = `
        <p class="guide-bubble">⚠️ 가이드를 불러오지 못했습니다.</p>
        <p class="guide-bubble">💡 직접 리뷰를 작성해주셔도 좋아요!</p>
      `;
        console.error("guide fetch error:", err);
      });
  // #endregion

  // #region 글자 수 카운터
  const reviewTextarea = document.getElementById("reviewContent");
  const charCounter = document.getElementById("charCounter");

  const updateCharCounter = () => {
    charCounter.textContent = `${reviewTextarea.value.length} / 200자`;
  };

  reviewTextarea.addEventListener("input", updateCharCounter);
  updateCharCounter();
  // #endregion

  // #region 내부 함수들
  function clearStars() { stars.forEach((star) => star.classList.remove("filled")); }
  function fillStars(index) { for (let i = 0; i <= index; i++) stars[i].classList.add("filled"); }
  function setOpacity(limit, value) {
    for (let i = 0; i <= limit && i < stars.length; i++) {
      if (stars[i].classList.contains("filled")) stars[i].style.opacity = value;
    }
  }
  function applyCheckedRating() {
    const selected = document.querySelector('.rating input[type="radio"]:checked');
    if (selected) {
      const prev = getPreviousSiblings(selected.parentElement);
      clearStars();
      prev.forEach((label) => label.querySelector(".star-icon").classList.add("filled"));
      selected.nextElementSibling.classList.add("filled");
      document.getElementById("ratingValue").textContent = `현재 선택: ${(parseInt(selected.value) / 2).toFixed(1)}점`;
    }
  }
  function getPreviousSiblings(labelEl) {
    const siblings = [];
    let prev = labelEl.previousElementSibling;
    while (prev) { siblings.push(prev); prev = prev.previousElementSibling; }
    return siblings.reverse();
  }
  // #endregion
});
