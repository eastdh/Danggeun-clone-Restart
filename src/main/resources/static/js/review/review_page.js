document.addEventListener("DOMContentLoaded", function () {
  const reviewForm = document.getElementById("reviewForm");

  // #region 별점 처리
  const ratingWrap = document.querySelector(".rating");
  const stars = ratingWrap.querySelectorAll(".star-icon");
  const ratingDisplay = document.createElement("div");

  // ⭐ 실시간 점수 표시 영역 추가
  ratingDisplay.id = "ratingValue";
  ratingDisplay.className = "rating-value-display";
  ratingDisplay.textContent = "현재 선택: 0점";
  ratingWrap.after(ratingDisplay);

  // 초기 선택된 값에 따라 별 채우기
  applyCheckedRating();

  // hover 처리
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

  // 등록 버튼
  reviewForm.addEventListener("submit", function (e) {
    e.preventDefault(); // 실제 사용 시 제거

    const score = getSelectedScore();
    console.log("선택된 별점 점수 (1~10):", score);

    // 점수 출력
    updateRatingDisplay(score);

    // 서버 전송 로직 예시
    // e.target.submit();
  });

  // 별 초기화
  function clearStars() {
    stars.forEach((star) => {
      star.classList.remove("filled");
    });
  }

  // hover 또는 선택에 따른 채우기
  function fillStars(index) {
    for (let i = 0; i <= index; i++) {
      stars[i].classList.add("filled");
    }
  }

  // 선택된 값 기준으로 채우기
  function applyCheckedRating() {
    const selected = document.querySelector('.rating input[type="radio"]:checked');
    if (selected) {
      const prevSiblings = getPreviousSiblings(selected.parentElement);
      clearStars();
      prevSiblings.forEach((label) => {
        label.querySelector(".star-icon").classList.add("filled");
      });
      selected.nextElementSibling.classList.add("filled");
      updateRatingDisplay(parseInt(selected.value));
    }
  }

  // opacity 처리
  function setOpacity(limit, value) {
    for (let i = 0; i <= limit && i < stars.length; i++) {
      if (stars[i].classList.contains("filled")) {
        stars[i].style.opacity = value;
      }
    }
  }

  // 점수 추출
  function getSelectedScore() {
    const selected = document.querySelector('.rating input[type="radio"]:checked');
    return selected ? parseInt(selected.value, 10) : 0;
  }

  // 실시간 점수 표시
  function updateRatingDisplay(score) {
    ratingDisplay.textContent = `현재 선택: ${score}점`;
  }

  // 이전 라벨들 가져오기
  function getPreviousSiblings(labelEl) {
    const siblings = [];
    let prev = labelEl.previousElementSibling;
    while (prev) {
      siblings.push(prev);
      prev = prev.previousElementSibling;
    }
    return siblings.reverse(); // 순서 보존
  }
  // #endregion

  // #region 키워드 선택 검증 로직
  const keywordInputs = reviewForm.querySelectorAll('input[name="keywords"]');

  reviewForm.addEventListener("submit", function (e) {
    const checkedKeywords = Array.from(keywordInputs).filter((input) => input.checked);
    const count = checkedKeywords.length;

    if (count < 1 || count > 5) {
      e.preventDefault();
      alert("키워드를 최소 1개, 최대 5개까지 선택해주세요.");
    }
  });

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
});
