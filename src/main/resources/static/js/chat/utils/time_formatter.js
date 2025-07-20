// resources/static/js/chat/utils/time_formatter.js
export function formatKoreanTime(date) {
  return date.toLocaleTimeString("ko-KR", {
    hour: "numeric",
    minute: "2-digit",
  });
}

export function isKoreanTimeString(text) {
  const pattern = /^(오전|오후)\s\d{1,2}:\d{2}$/;
  return typeof text === "string" && pattern.test(text);
}
