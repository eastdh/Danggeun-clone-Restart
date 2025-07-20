// resources/static/js/chat/ws/web_socket_manager.js
import { WS, RECONNECT_POLICY } from "../constants.js";

/**
 * WebSocketManager
 * - STOMP 연결 및 구독 관리
 * - reconnect/backoff 정책 적용
 * - 이벤트(dispatchEvent) 기반으로 외부에 메세지 전달
 */
export class WebSocketManager extends EventTarget {
  constructor(userId) {
    super();
    this.userId = userId;
    this.stompClient = null;

    this.attempts = 0;
    this.summaryCache = new Map();
  }

  /**
   * 지정된 채팅방(roomId)으로 연결/구독을 시작
   */
  connect(roomId) {
    this.roomId = roomId;
    this._disconnect(); // 기존 연결 정리
    this._connect(); // 새 연결 시도
  }

  /** 내부: STOMP 연결 및 초기 구독 설정 */
  _connect() {
    const socket = new SockJS(WS.ENDPOINT);
    this.stompClient = Stomp.over(socket);
    this.stompClient.debug = (msg) => console.log("STOMP ▶", msg); // 디버그 로그 비활성화

    this.stompClient.connect(
      {},
      // 연결 성공 콜백
      () => {
        this.attempts = 0;
        this._subscribeChatList();
        this._subscribeChatRoom(this.roomId);
        this._subscribeReadReceipt(this.roomId);

        // 입장 시점 읽음 처리
        this.sendReadReceipt();
      },
      // 연결 실패 콜백
      () => {
        this._scheduleReconnect();
      }
    );
  }

  /** 내부: 채팅 리스트 요약 구독 */
  _subscribeChatList() {
    this.stompClient.subscribe(WS.TOPIC.CHAT_LIST, ({ body }) => {
      const summary = JSON.parse(body);
      const prev = this.summaryCache.get(summary.chatRoomId);

      // “isNew” 판단 로직: 마지막 메시지 또는 badge 변화 시
      const isNew = prev && (prev.lastMessageContent !== summary.lastMessageContent || prev.unreadCount !== summary.unreadCount || summary.unreadCount > 0);

      this.summaryCache.set(summary.chatRoomId, summary);
      this.dispatchEvent(new CustomEvent("chatListUpdate", { detail: { summary, isNew } }));
    });
  }

  /** 내부: 특정 방 토픽 구독 */
  _subscribeChatRoom(roomId) {
    this.topicChat = this.stompClient.subscribe(WS.TOPIC.CHAT_ROOM(roomId), ({ body }) => {
      const msg = JSON.parse(body);
      this.dispatchEvent(new CustomEvent("chatMessage", { detail: msg }));

      // 항상 읽음 ACK 전송
      this.sendReadReceipt([msg.messageId]);
    });
  }

  /** 내부: 읽음 ACK 토픽 구독 */
  _subscribeReadReceipt(roomId) {
    this.topicRead = this.stompClient.subscribe(WS.TOPIC.CHAT_ROOM_READ(roomId), ({ body }) => {
      const receipt = JSON.parse(body);
      this.dispatchEvent(new CustomEvent("readReceipt", { detail: receipt }));
    });
  }

  /** 클라이언트 → 서버: 메시지 전송 */
  sendMessage(payload) {
    if (!this._isConnected()) return;
    this.stompClient.send(WS.SEND_MESSAGE, {}, JSON.stringify(payload));
  }

  /** 클라이언트 → 서버: 읽음 ACK */
  sendReadReceipt(messageIds = []) {
    if (!this._isConnected()) return;
    const ack = { chatRoomId: this.roomId, readerId: Number(this.userId) };
    if (messageIds.length) ack.messageIds = messageIds;
    this.stompClient.send(WS.SEND_READ, {}, JSON.stringify(ack));
  }

  /** 명시적 연결 해제 */
  disconnect() {
    this._disconnect();
    this.summaryCache.clear();
  }

  /** 내부: 연결 유효성 검사 */
  _isConnected() {
    return this.stompClient && this.stompClient.connected;
  }

  /** 내부: 연결 해제 (구독도 함께 해제) */
  _disconnect() {
    if (this.topicChat) this.topicChat.unsubscribe();
    if (this.topicRead) this.topicRead.unsubscribe();
    if (this.stompClient) this.stompClient.disconnect();
    this.stompClient = null;
  }

  /** 내부: 재연결 스케줄링 (지수 Backoff) */
  _scheduleReconnect() {
    if (this.attempts >= RECONNECT_POLICY.MAX_ATTEMPTS) {
      this.dispatchEvent(new Event("reconnectFailed"));
      return;
    }
    this.attempts++;
    const delay = RECONNECT_POLICY.INITIAL_DELAY * RECONNECT_POLICY.BACKOFF_FACTOR ** (this.attempts - 1);
    setTimeout(() => this._connect(), delay);
  }
}
