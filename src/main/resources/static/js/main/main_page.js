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
  const titleEl = previewMain.querySelector(".preview-main__title");
  const descEl = previewMain.querySelector(".preview-main__desc");

  // 1) img 컨테이너 준비 (원래 <img> 자리 비우고)
  const imgContainer = document.createElement("div");
  imgContainer.className = "preview-main__imgs";
  previewMain.querySelector(".preview-main__img").remove();
  previewMain.prepend(imgContainer);

  // 2) 작은 카드 이미지 클론해서 컨테이너에 추가
  const imgs = Array.from(previewCards).map((card, i) => {
    const src = card.dataset.src;
    const img = document.createElement("img");
    img.src = src;
    img.className = "preview-main__img";
    img.style.display = i === 0 ? "block" : "none";
    imgContainer.appendChild(img);
    return img;
  });

  let currentIndex = 0;
  let isPaused = false;

  function updatePreview(idx) {
    if (idx < 0 || idx >= imgs.length) return;
    // 이미지 토글
    imgs.forEach((img, i) => {
      img.style.display = i === idx ? "block" : "none";
    });
    // 제목·설명 갱신
    const card = previewCards[idx];
    titleEl.textContent = card.dataset.title;
    descEl.textContent = card.dataset.desc;

    currentIndex = idx;

    previewCards.forEach((c) => c.classList.remove("active"));
    card.classList.add("active");
  }

  // 초기 렌더
  updatePreview(0);

  // 자동 순환
  setInterval(() => {
    if (!isPaused) {
      updatePreview((currentIndex + 1) % imgs.length);
    }
  }, 3000);

  // hover로 일시정지 + 클릭 시 해당 인덱스 강제 렌더
  previewCards.forEach((card, idx) => {
    card.addEventListener("mouseenter", () => {
      isPaused = true;
      updatePreview(idx);
    });
    card.addEventListener("mouseleave", () => {
      isPaused = false;
    });
  });
  previewMain.addEventListener("mouseenter", () => (isPaused = true));
  previewMain.addEventListener("mouseleave", () => (isPaused = false));
  // #endregion
});
