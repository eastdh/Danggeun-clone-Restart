// resources/static/js/chat/managers/chat_room_manager.js
import { API_PATHS, SENDER_TYPES, MESSAGE_TYPES, WS } from "../constants.js";

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
      } catch (err) {
        console.error("채팅방 로드 실패:", err);
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

    // 챗봇 응답 수신
    this.wsManager.addEventListener("chatBotMessage", (e) => {
      const msg = e.detail;
      this.store.appendMessage({
        ...msg,
        senderType: SENDER_TYPES.CHAT_BOT,
        timestamp: msg.timestamp,
      });
    });
  }

  async _loadRoomDetail(roomId) {
    try {
      if (roomId < 0) {
        console.log("[ChatRoomManager] 챗봇 방 로드");
        // 챗봇 방은 API 호출 안함
        const botDetail = {
          chatRoomId: roomId,
          partnerNickname: "챗봇",
          partnerTemperature: null,
          tradeTitle: null,
          tradePrice: null,
          tradeThumbnailUrl: null,
          closed: true, // 거래 버튼 숨기기용 플래그
          seller: false,
        };
        this.store.setCurrentRoomDetail(botDetail);

        const storageKey = `chatbot_history_${this.store.userId}`;
        const cached = JSON.parse(localStorage.getItem(storageKey)) || [];
        if (cached.length > 0) {
          console.log("[ChatRoomManager] 챗봇 메시지 캐시 로드", cached);
          this.store.setMessages(cached);
        } else {
          console.log("[ChatRoomManager] 챗봇 방 초기화");
          // 최초 진입 시 환영 메시지
          this.store.setMessages([
            {
              messageId: null,
              senderId: null,
              senderType: SENDER_TYPES.CHAT_BOT,
              content: "안녕하세요! 무엇을 도와드릴까요?",
              timestamp: new Date().toISOString(),
              isRead: false,
              messageType: MESSAGE_TYPES.TEXT,
            },
            {
              messageId: null,
              senderId: null,
              senderType: SENDER_TYPES.CHAT_BOT,
              content: "당근 마켓이 무엇인가요?",
              timestamp: new Date().toISOString(),
              isRead: false,
              messageType: MESSAGE_TYPES.CHAT_BOT,
            },
            {
              messageId: null,
              senderId: null,
              senderType: SENDER_TYPES.CHAT_BOT,
              content: "당근 마켓을 이용하는 방법을 알려주세요.",
              timestamp: new Date().toISOString(),
              isRead: false,
              messageType: MESSAGE_TYPES.CHAT_BOT,
            },
            {
              messageId: null,
              senderId: null,
              senderType: SENDER_TYPES.CHAT_BOT,
              content: "챗봇으로 무엇을 할 수 있나요?",
              timestamp: new Date().toISOString(),
              isRead: false,
              messageType: MESSAGE_TYPES.CHAT_BOT,
            },
          ]);

          // 메시지가 렌더링된 이후 바인딩
          setTimeout(() => {
            document.querySelectorAll(".room__messages__item--chat-bot .room__messages__item__text").forEach((el) => {
              el.addEventListener("click", () => {
                const selectedMessage = el.textContent;
                this.handleBotExampleClick(selectedMessage);
              });
            });
          }, 0);
          console.log("[ChatRoomManager] 챗봇 방 메시지 하드 코딩 완료");
        }
      } else {
        const { detail, messages } = await this.api.getChatRoomDetail(roomId);
        this.store.setCurrentRoomDetail(detail);
        this.store.setMessages(messages);
      }
    } catch (err) {
      console.error("[ChatRoomManager] _loadRoomDetail 에러", err);
      throw err;
    }
  }

  _connectWebSocket(roomId) {
    const userId = this.store.userId;
    this.wsManager.connect(roomId, userId);
    // 입장 시점 읽음 처리도 wsManager에서 진행
  }

  async _handleSend() {
    const content = this.renderer.inputField.value.trim();
    if (!content) return;

    // 1) 사용자 메시지 즉시 렌더 (Optimistic UI)
    this.store.appendMessage({
      chatRoomId: this.store.currentRoomId,
      senderId: this.store.userId,
      content,
      senderType: SENDER_TYPES.ME,
      timestamp: Date.now(),
      tempMessage: true,
      isRead: false,
      messageType: "TEXT",
    });
    this.renderer.inputField.value = "";

    // 2) 챗봇 방이면 REST 호출만 트리거하고,
    //    WS 구독(chatBotMessage)에서 답변을 받는다
    if (this.store.currentRoomId < 0) {
      try {
        await this.api.chatBot({
          chatRoomId: -this.store.userId,
          senderId: this.store.userId,
          content,
        });
      } catch {
        // Toast.error("챗봇 요청 실패", 3000);
      }
    } else {
      // 일반 방은 기존 WS 전송
      this.wsManager.sendMessage({
        chatRoomId: this.store.currentRoomId,
        senderId: this.store.userId,
        content,
        messageType: "TEXT",
      });
    }
  }

  handleBotExampleClick(selectedMessage) {
    // ① 챗봇 예시 메시지 모두 제거
    document.querySelectorAll(".room__messages__item--chat-bot").forEach((el) => el.remove());

    // ② 내가 보낸 메시지 렌더링
    const payload = {
      chatRoomId: this.store.currentRoomId,
      senderId: this.store.userId,
      content: selectedMessage,
      senderType: SENDER_TYPES.ME,
      tempMessage: true,
      isRead: false,
      timestamp: Date.now(),
      messageType: "TEXT",
    };
    this.store.appendMessage(payload);

    // ③ 서버에 챗봇 요청
    this.api.chatBot(payload).catch((err) => {
      console.error("챗봇 요청 실패:", err);
      Toast.error("챗봇 응답 요청 중 오류가 발생했습니다");
    });
  }
}
