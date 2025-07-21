// resources/static/js/chat/constants.js
// 상수 정의 파일
// 이 파일은 채팅 애플리케이션에서 사용되는 상수들을 정의합니다.

export const BOT_ROOM_ID = -1; // 챗봇 방 ID

// 메시지 타입(enum)
export const MESSAGE_TYPES = {
  TEXT: "TEXT",
  IMAGE: "IMAGE",
  DATE_LABEL: "DATE_LABEL",
  SYSTEM: "SYSTEM",
  TRADE_CONFIRM: "TRADE_CONFIRM",
  CHAT_BOT: "CHAT_BOT",
};

// 발신자 타입(enum)
export const SENDER_TYPES = {
  ME: "ME",
  PARTNER: "PARTNER",
  SYSTEM: "SYSTEM",
  DATE_LABEL: "DATE_LABEL",
  CHAT_BOT: "CHAT_BOT",
};

// REST API 경로
export const API_PATHS = {
  CHAT_LIST: "/api/chat/list",
  CHAT_ROOM_DETAIL: (chatRoomId) => `/api/chat/room/${chatRoomId}`,
  CONFIRM_TRADE: "/api/chat/confirm-trade",
  MARK_READ: "/api/chat/message/read",
  CHAT_BOT: "/api/chat/bot",
};

// WebSocket/STOMP 설정
export const WS = {
  ENDPOINT: "/ws-chat",
  SEND_MESSAGE: "/app/send",
  SEND_READ: "/app/read",
  SEND_BOT: "/api/chat/bot",
  TOPIC: {
    CHAT_LIST: "/user/queue/chat-list",
    CHAT_ROOM: (roomId) => `/topic/chat/${roomId}`,
    CHAT_ROOM_READ: (roomId) => `/topic/chat/${roomId}/read`,
    CHAT_BOT: (roomId) => `/topic/chat/bot/${roomId}`,
  },
};

// 주요 CSS 셀렉터 모음
export const SELECTORS = {
  CHAT_ROOM_LIST_ITEM: ".list__room-list__item",
  UNREAD_BADGE: ".unread-badge",
  MESSAGE_CONTAINER: ".room__messages",
  MESSAGE_INPUT: ".room__input-area__field",
  SEND_BUTTON: ".room__input-area__send-button",
  TRADE_STATUS_BUTTON: "#tradeStatusButton",
};

// WebSocket 재연결 정책
export const RECONNECT_POLICY = {
  MAX_ATTEMPTS: 5,
  INITIAL_DELAY: 1000, // ms
  BACKOFF_FACTOR: 1.5,
};
