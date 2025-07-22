// resources/static/js/chat/api/api_client.js

import { API_PATHS } from "../constants.js";

export class ApiClient {
  constructor({ onLoading, onError } = {}) {
    // 로딩 시작/종료, 에러 콜백을 외부에서 주입할 수 있음
    this.onLoading = onLoading || (() => {});
    this.onError = onError || ((err) => console.error(err));
  }

  // 공통 요청 처리
  async request(url, options = {}) {
    this.onLoading(true);
    try {
      const res = await fetch(url, {
        headers: { "Content-Type": "application/json" },
        ...options,
      });

      if (!res.ok) {
        const errorBody = await res.text();
        throw new Error(`API error ${res.status}: ${errorBody}`);
      }

      // 204 No Content 혹은 빈 바디인 경우 빈 객체
      const contentType = res.headers.get("Content-Type") || "";
      if (res.status === 204 || (!contentType.includes("application/json") && res.headers.get("Content-Length") === "0")) {
        return {};
      }

      // JSON이지만 body가 빈 문자열일 수 있으니 guard
      const text = await res.text();
      return text ? JSON.parse(text) : {};
    } catch (err) {
      this.onError(err);
      throw err;
    } finally {
      this.onLoading(false);
    }
  }

  // 채팅방 목록 조회
  async getChatList({ userId, showUnreadOnly = false }) {
    return this.request(API_PATHS.CHAT_LIST, {
      method: "POST",
      body: JSON.stringify({ userId, showUnreadOnly }),
    });
  }

  // 특정 채팅방 상세 + 메시지 조회
  async getChatRoomDetail(chatRoomId) {
    return this.request(API_PATHS.CHAT_ROOM_DETAIL(chatRoomId), {
      method: "GET",
    });
  }

  // 거래 확정 API 호출
  async confirmTrade(tradeId, chatRoomId) {
    return this.request(API_PATHS.CONFIRM_TRADE, {
      method: "POST",
      body: JSON.stringify({ tradeId, chatRoomId }),
    });
  }

  // 읽음 처리 API 호출
  async markAsRead(chatRoomId, userId) {
    return this.request(API_PATHS.MARK_READ, {
      method: "PATCH",
      body: JSON.stringify({ chatRoomId, userId }),
    });
  }

  // 챗봇 메시지 전송
  async chatBot(payload) {
    console.log("ChatBot API 호출 URL:", API_PATHS.CHAT_BOT, payload);
    return this.request(API_PATHS.CHAT_BOT, {
      method: "POST",
      body: JSON.stringify(payload),
    });
  }

  async createOrGetChatRoom({ tradeId, receiverId }) {
    return this.request(API_PATHS.CREATE_CHAT_ROOM, {
      method: "POST",
      body: JSON.stringify({ tradeId, receiverId }),
    });
  }
}
