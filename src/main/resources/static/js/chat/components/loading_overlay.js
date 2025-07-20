// resources/static/js/chat/components/loading_overlay.js
export class LoadingOverlay {
  constructor(selector = "#loadingIndicator") {
    this.el = document.querySelector(selector);
    this.messageEl = this.el.querySelector(".loading-message");
  }

  show(message = "로딩 중입니다...") {
    if (message) this.messageEl.textContent = message;
    this.el.style.display = "flex";
  }

  hide() {
    this.el.style.display = "none";
  }
}
