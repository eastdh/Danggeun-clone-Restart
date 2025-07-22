// resources/static/js/chat/index.js

import { LoadingOverlay } from "./components/loading_overlay.js";
import { Toast } from "./components/toast.js";
import { ApiClient } from "./api/api_client.js";
import { WebSocketManager } from "./ws/web_socket_manager.js";
import { ChatStore } from "./store/chat_store.js";
import { Renderer } from "./renderer/renderer.js";
import { ChatListManager } from "./managers/chat_list_manager.js";
import { ChatRoomManager } from "./managers/chat_room_manager.js";
import { SELECTORS } from "./constants.js";

document.addEventListener("DOMContentLoaded", () => {
  // 1) 사용자 ID 읽기
  const userEl = document.querySelector(SELECTORS.USER_ID) || document.querySelector(".list__header__user-id");
  const userId = userEl?.dataset.userId;
  if (!userId) {
    Toast.error("사용자 정보가 없습니다.");
    return;
  }

  // 2) UI 컴포넌트
  const overlay = new LoadingOverlay();
  const toast = new Toast();

  // 3) API 클라이언트
  const api = new ApiClient({
    onLoading: (isLoading) => (isLoading ? overlay.show() : overlay.hide()),
    onError: (err) => toast.error(err.message),
  });

  // 4) WebSocket 매니저
  const wsManager = new WebSocketManager(userId);

  // 5) 상태 스토어
  const store = new ChatStore();
  store.setUserId(Number(userId));
  const params = new URLSearchParams(window.location.search);
  if (params.has("roomId")) {
    const roomId = parseInt(params.get("roomId"), 10);
    store.currentRoomId = roomId;
  }

  // 6) 렌더러
  const renderer = new Renderer(store);

  // 7) 매니저 연결
  new ChatListManager(store, api, renderer);
  new ChatRoomManager(store, api, wsManager, renderer);
});
document.addEventListener("DOMContentLoaded", () => {
  document.body.addEventListener("click", (e) => {
    const target = e.target;

    // 후기 작성 버튼
    if (target.classList.contains("review-button")) {
      const tradeId = target.dataset.tradeId;
      const partnerId = target.dataset.partnerId;
      const isSeller = target.dataset.isSeller;
      const chatRoomId = target.dataset.chatRoomId;

      if (tradeId && partnerId) {
        window.location.href =
            `/review/write?trade_id=${tradeId}&partner_id=${partnerId}&is_seller=${isSeller}&chat_room_id=${chatRoomId}`;
      } else {
        console.error("필수 데이터(tradeId, partnerId)가 누락되었습니다.");
      }
    }

    // 후기 확인 버튼
    if (target.classList.contains("review-check-button")) {
      const tradeId = target.dataset.tradeId;
      const partnerId = target.dataset.partnerId;

      if (tradeId && partnerId) {
        window.location.href =
            `/review/review_view?trade_id=${tradeId}&partner_id=${partnerId}`;
      } else {
        console.error("필수 데이터(tradeId, partnerId)가 누락되었습니다.");
      }
    }
  });
});
