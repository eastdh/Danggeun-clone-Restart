document.addEventListener("DOMContentLoaded", function () {
  const btn = document.getElementById("userProfileBtn");
  const dropdown = document.getElementById("profileDropdown");

  if (btn && dropdown) {
    btn.addEventListener("click", function (e) {
      e.stopPropagation();
      dropdown.style.display =
        dropdown.style.display === "block" ? "none" : "block";
    });

    document.addEventListener("click", function (e) {
      if (!dropdown.contains(e.target) && e.target !== btn) {
        dropdown.style.display = "none";
      }
    });
  }

  search();
});

function search() {
  const searchInput = document.getElementById("search-input");
  searchInput.addEventListener("keydown", (e) => {
    if (e.key === "Enter") {
      // Todo : 입력값 유효성 검사
      const keyword = searchInput.value.trim();
      const params = new URLSearchParams(window.location.search);
      const serviceLocation = params.has("location")
        ? params.get("location")
        : null;
      if (serviceLocation) {
        window.location.href = `/trade?keyword=${keyword}&location=${serviceLocation}`;
      } else {
        window.location.href = `/trade?keyword=${keyword}`;
      }
    }
  });
}
