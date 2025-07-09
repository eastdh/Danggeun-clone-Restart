document.addEventListener("DOMContentLoaded", () => {
  // region Scroll Indicator
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
  // endregion
});
