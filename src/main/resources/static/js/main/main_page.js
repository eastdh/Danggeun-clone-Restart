// main_page.js
document.addEventListener("DOMContentLoaded", () => {
  // #region Scroll Indicator
  const slides = document.querySelectorAll(".slide");
  const indicatorContainer = document.getElementById("scrollIndicator");

  // dot 자동 생성
  slides.forEach((slide, index) => {
    const dot = document.createElement("div");
    dot.classList.add("dot");
    if (index === 0) dot.classList.add("active"); // 초기 활성화

    const label = slide.dataset.label || `섹션 ${index + 1}`;
    dot.setAttribute("title", label);

    dot.addEventListener("click", () => {
      slides[index].scrollIntoView({ behavior: "smooth" });
    });
    indicatorContainer.appendChild(dot);
  });

  const dots = document.querySelectorAll(".dot");

  // IntersectionObserver로 활성화 dot 감지
  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          const index = [...slides].indexOf(entry.target);
          dots.forEach((dot) => dot.classList.remove("active"));
          if (dots[index]) dots[index].classList.add("active");
        }
      });
    },
    {
      root: document.querySelector(".scroll-container"),
      threshold: 0.6, // 슬라이드가 60% 이상 보일 때 감지
    }
  );

  slides.forEach((slide) => observer.observe(slide));
  // #endregion

  // #region UI Preview 자동 순회 + 인터랙션
  const previewCards = document.querySelectorAll(".preview-card");
  const previewMain = document.getElementById("previewMain");
  const previewImg = previewMain?.querySelector(".preview-main__img");
  const previewTitle = previewMain?.querySelector(".preview-main__title");
  const previewDesc = previewMain?.querySelector(".preview-main__desc");

  let currentIndex = 0;
  let isPaused = false;

  function updatePreview(index) {
    const card = previewCards[index];
    if (!card) return;

    const src = card.dataset.src;
    const title = card.dataset.title;
    const desc = card.dataset.desc;

    previewImg.src = `/src/main/resources/static/assets/screenshots/${src}`;
    previewTitle.textContent = title;
    previewDesc.textContent = desc;

    previewCards.forEach((c) => c.classList.remove("active"));
    card.classList.add("active");

    currentIndex = index;
  }

  // 초기 표시
  updatePreview(0);

  // 순회 애니메이션 (3초마다 전환)
  let rotationInterval = setInterval(() => {
    if (isPaused) return;
    let next = (currentIndex + 1) % previewCards.length;
    updatePreview(next);
  }, 3000);

  // 카드 hover로 일시정지 및 수동 표시
  previewCards.forEach((card, index) => {
    card.addEventListener("mouseenter", () => {
      isPaused = true;
      updatePreview(index);
    });
    card.addEventListener("mouseleave", () => {
      isPaused = false;
    });
  });

  // 중앙 preview 영역에 hover → 순회 일시정지
  previewMain?.addEventListener("mouseenter", () => {
    isPaused = true;
  });

  previewMain?.addEventListener("mouseleave", () => {
    isPaused = false;
  });
  // #endregion
});
