// resources/static/js/chat/components/toast.js
export class Toast {
  constructor(containerSelector = "body") {
    this.container = document.querySelector(containerSelector);
    this._initContainer();
  }

  _initContainer() {
    this.toastEl = document.createElement("div");
    this.toastEl.className = "chat-toast-container";
    this.container.appendChild(this.toastEl);
  }

  _show(message, type = "error", duration = 3000) {
    const msgEl = document.createElement("div");
    msgEl.className = `chat-toast chat-toast--${type}`;
    msgEl.textContent = message;
    this.toastEl.appendChild(msgEl);

    setTimeout(() => {
      msgEl.classList.add("fade-out");
      msgEl.addEventListener("transitionend", () => msgEl.remove());
    }, duration);
  }

  error(msg, duration) {
    this._show(msg, "error", duration);
  }

  success(msg, duration) {
    this._show(msg, "success", duration);
  }

  info(msg, duration) {
    this._show(msg, "info", duration);
  }
}
