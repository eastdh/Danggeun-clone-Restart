// resources/static/js/chat/managers/chat_room_manager.js
import { API_PATHS, SENDER_TYPES, WS } from "../constants.js";

/**
 * ChatRoomManager
 * @param {ChatStore} store
 * @param {ApiClient} api
 * @param {WebSocketManager} wsManager
 * @param {Renderer} renderer
 */
export class ChatRoomManager {
  constructor(store, api, wsManager, renderer) {
    this.store = store;
    this.api = api;
    this.wsManager = wsManager;
    this.renderer = renderer;

    this._bindStoreEvents();
    this._bindUIEvents();
    this._bindWsEvents();
  }

  _bindStoreEvents() {
    // 방이 바뀌면 상세 + 메시지 로드
    this.store.addEventListener("currentRoomChanged", async (e) => {
      const roomId = e.detail;
      this.renderer.showLoading("채팅방을 불러오는 중입니다...");
      this.renderer.hideRoom();
      try {
        await this._loadRoomDetail(roomId);
        this._connectWebSocket(roomId);
        this.renderer.showRoom();
      } catch {
        this.renderer.showEmptyRoom();
      } finally {
        this.renderer.hideLoading();
      }
    });
  }

  _bindUIEvents() {
    // 전송 버튼
    this.renderer.sendButton.addEventListener("click", () => this._handleSend());
    // Enter 키
    this.renderer.inputField.addEventListener("keydown", (evt) => {
      if (evt.key === "Enter" && !evt.shiftKey && !evt.ctrlKey) {
        evt.preventDefault();
        this._handleSend();
      }
    });
    // 거래 확정 버튼
    this.renderer.tradeStatusBtn.addEventListener("click", () => {
      const tradeId = Number(this.renderer.tradeStatusBtn.dataset.tradeId);
      this.api
        .confirmTrade(tradeId)
        .then(() => this.store.setCurrentRoom(this.store.currentRoomId))
        .catch(() => {
          /* Toast 알림 */
        });
    });
  }

  _bindWsEvents() {
    // 리스트 요약 업데이트
    this.wsManager.addEventListener("chatListUpdate", (e) => {
      const { summary, isNew } = e.detail;
      this.store.updateChatRoomSummary(summary, isNew);
    });

    // 새 메시지 수신
    this.wsManager.addEventListener("chatMessage", (e) => {
      const msg = e.detail;

      if (msg.senderId === this.store.userId) {
        this.renderer.removeTempMessage();
        this.store.appendMessage({
          ...msg,
          senderType: SENDER_TYPES.ME,
          timestamp: msg.timestamp,
        });
      } else {
        this.store.appendMessage({
          ...msg,
          senderType: SENDER_TYPES.PARTNER,
          timestamp: msg.timestamp,
        });
      }
    });

    // 읽음 ACK 수신
    this.wsManager.addEventListener("readReceipt", (e) => {
      const { readerId, messageIds } = e.detail;

      // 읽은 사람이 나(=this.store.userId)가 아니라면,
      // “내가 보낸” 메시지들의 read 상태를 true로 바꾼다
      if (Number(readerId) !== Number(this.store.userId)) {
        console.log("[WS] 상대방이 읽음 수신 →", e.detail);
        this.store.markMessagesRead(messageIds);
      }
    });
  }

  async _loadRoomDetail(roomId) {
    try {
      const { detail, messages } = await this.api.getChatRoomDetail(roomId);
      this.store.setCurrentRoomDetail(detail);
      this.store.setMessages(messages);
    } catch (err) {
      // error 처리
    }
  }

  _connectWebSocket(roomId) {
    const userId = this.store.userId;
    this.wsManager.connect(roomId, userId);
    // 입장 시점 읽음 처리도 wsManager에서 진행
  }

  _handleSend() {
    const content = this.renderer.inputField.value.trim();
    if (!content) return;

    const tempMessage = true;

    const payload = {
      chatRoomId: this.store.currentRoomId,
      senderId: this.store.userId,
      content,
      messageType: "TEXT",
      senderType: SENDER_TYPES.ME,
      tempMessage,
    };

    // Optimistic UI
    this.store.appendMessage({ ...payload, timestamp: Date.now(), senderType: SENDER_TYPES.ME, isRead: false, tempMessage: true });

    // 전송
    this.wsManager.sendMessage(payload);
    this.renderer.inputField.value = "";
  }
}
