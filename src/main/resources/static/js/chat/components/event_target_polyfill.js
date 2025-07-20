// resources/static/js/chat/components/event_target_polyfill.js
(function () {
  if (typeof window.EventTarget === "function") return;
  function EventTargetPoly() {
    this.listeners = {};
  }
  EventTargetPoly.prototype.addEventListener = function (type, callback) {
    (this.listeners[type] = this.listeners[type] || []).push(callback);
  };
  EventTargetPoly.prototype.removeEventListener = function (type, callback) {
    if (!this.listeners[type]) return;
    this.listeners[type] = this.listeners[type].filter((cb) => cb !== callback);
  };
  EventTargetPoly.prototype.dispatchEvent = function (event) {
    const { type } = event;
    (this.listeners[type] || []).forEach((callback) => callback(event));
  };
  window.EventTarget = EventTargetPoly;
})();

// 모듈로 export
export const EventTargetPolyfill = window.EventTarget;
