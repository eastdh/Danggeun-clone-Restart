let currentIndex = 0;

// carousel
function updateCarousel() {
  const track = document.getElementById("carousel-track");
  const items = document.querySelectorAll(".carousel-item");
  const indicators = document.querySelectorAll(".carousel-indicators button");

  // slide
  track.style.transform = `translateX(-${currentIndex * 100}%)`;

  // indicator update
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

// tooltip box
function toggleTooltip(event) {
  const tooltip = event.currentTarget.nextElementSibling;
  tooltip.classList.toggle('active');
}

document.addEventListener('click', function(e) {
  document.querySelectorAll('.manner-tooltip.active').forEach(tt => {
    const label = tt.previousElementSibling;
    if (!label.contains(e.target) && !tt.contains(e.target)) {
      tt.classList.remove('active');
    }
  });
});
