document.addEventListener("DOMContentLoaded", function () {
  const reviewForm = document.getElementById("reviewForm");

  // #region 별점 처리
  const ratingWrap = document.querySelector(".rating");
  const stars = ratingWrap.querySelectorAll(".star-icon");

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
    e.preventDefault(); // TODO: 실제 사용 시 제거

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
    const display = document.getElementById("ratingValue");
    if (!display) return;

    if (score > 0) {
      const realScore = (score / 2).toFixed(1); // 1~10 → 0.5 ~ 5.0
      display.textContent = `현재 선택: ${realScore}점`;
    } else {
      display.textContent = ""; // 선택하지 않았으면 아무것도 출력 안함
    }
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
      const fallback = `
        <p class="guide-bubble">⚠️ 가이드를 불러오지 못했습니다.</p>
        <p class="guide-bubble">💡 직접 리뷰를 작성해주셔도 좋아요!</p>
      `;
      document.getElementById("reviewGuide").innerHTML = fallback;
      console.error("guide fetch error:", err);
    });

  // #endregion

  // #region 리뷰 내용 글자 수 카운터
  const reviewTextarea = document.getElementById("reviewContent");
  const charCounter = document.getElementById("charCounter");

  const updateCharCounter = () => {
    const length = reviewTextarea.value.length;
    charCounter.textContent = `${length} / 200자`;
  };

  reviewTextarea.addEventListener("input", updateCharCounter);

  // 페이지 로딩 시 초기값 반영
  updateCharCounter();
  // #endregion
});
