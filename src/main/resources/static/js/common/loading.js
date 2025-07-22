// loading.js
export function showLoading(message = "로딩 중입니다...") {
  const indicator = document.getElementById("loadingIndicator");
  indicator.querySelector(".loading-message").textContent = message;
  indicator.style.display = "flex";
}

export function hideLoading() {
  const indicator = document.getElementById("loadingIndicator");
  indicator.style.display = "none";
}
