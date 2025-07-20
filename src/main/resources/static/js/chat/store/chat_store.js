// resources/static/js/chat/store/chat_store.js
import { EventTargetPolyfill as EventTarget } from "../components/event_target_polyfill.js"; // IE 호환용 폴리필(필요 시)

/**
 * ChatStore
 * - 전체 애플리케이션 상태 보관
 * - 변경 시 CustomEvent로 알림
 */
export class ChatStore extends EventTarget {
  constructor() {
    super();
    this.userId = null;
    this.chatRooms = []; // ChatRoomSummaryDto[]
    this.showUnreadOnly = false;
    this.currentRoomId = null;
    this.currentRoomDetail = null; // ChatRoomDetailDto
    this.messages = []; // ChatMessageDto[]
  }

  // ---- 사용자 설정 ----
  setUserId(id) {
    this.userId = id;
    this.dispatchEvent(new CustomEvent("userIdChanged", { detail: id }));
  }

  // ---- 채팅방 목록 ----
  setChatRooms(list, showUnreadOnly = false) {
    this.chatRooms = list;
    this.showUnreadOnly = showUnreadOnly;
    this.dispatchEvent(
      new CustomEvent("chatRoomsChanged", {
        detail: { list, showUnreadOnly },
      })
    );
  }

  updateChatRoomSummary(summary, isNew) {
    // 기존에 있으면 교체, 없으면 삽입
    const idx = this.chatRooms.findIndex((r) => r.chatRoomId === summary.chatRoomId);
    if (idx > -1) {
      this.chatRooms[idx] = summary;
    } else {
      this.chatRooms.unshift(summary);
    }
    this.dispatchEvent(
      new CustomEvent("chatRoomUpdated", {
        detail: { summary, isNew },
      })
    );
  }

  // ---- 현재 방 상세 ----
  setCurrentRoom(roomId) {
    this.currentRoomId = roomId;
    this.dispatchEvent(new CustomEvent("currentRoomChanged", { detail: roomId }));
  }

  setCurrentRoomDetail(detailDto) {
    this.currentRoomDetail = detailDto;
    this.dispatchEvent(new CustomEvent("roomDetailChanged", { detail: detailDto }));
  }

  // ---- 메시지 목록 ----
  setMessages(messages) {
    this.messages = messages;
    this.dispatchEvent(new CustomEvent("messagesChanged", { detail: messages }));
  }

  appendMessage(msg) {
    this.messages.push(msg);
    this.dispatchEvent(new CustomEvent("messageAppended", { detail: msg }));
  }

  replaceMessage(tempId, newMsg) {
    console.log(`[STORE] replaceMessage 호출: tempId=${tempId}`, "newMsg=", newMsg);
    const idx = this.messages.findIndex((m) => m.tempId === tempId);
    if (idx > -1) {
      this.messages.splice(idx, 1, newMsg);
      this.dispatchEvent(new CustomEvent("messageReplaced", { detail: { tempId, newMsg } }));
      console.log("[STORE] replaceMessage 후 messages=", this.messages);
    }
  }

  markMessagesAsRead(messageIds) {
    this.messages = this.messages.map((m) => ({
      ...m,
      isRead: messageIds.includes(m.messageId) || m.isRead,
    }));
    this.dispatchEvent(new CustomEvent("messagesRead", { detail: messageIds }));
  }

  removeTempMessages() {
    this.messages = this.messages.filter((m) => !m.tempId);
    this.dispatchEvent(new CustomEvent("messagesChanged", { detail: this.messages }));
  }
}
