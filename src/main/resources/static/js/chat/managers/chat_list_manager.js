// resources/static/js/chat/managers/chat_list_manager.js
import { SELECTORS } from "../constants.js";

/**
 * ChatListManager
 * @param {ChatStore} store
 * @param {ApiClient} api
 * @param {Renderer} renderer
 */
export class ChatListManager {
  constructor(store, api, renderer) {
    this.store = store;
    this.api = api;
    this.renderer = renderer;

    this._cacheElements();
    this._bindUIEvents();
    this._bindStoreEvents();

    // 최초 목록 로드
    this.loadList().then(() => {
      this._autoSelectRoom();
    });
  }

  _cacheElements() {
    this.listContainer = document.querySelector(".list__room-list");
    this.toggleControl = document.querySelector(".list__header__toggle-switch input");
  }

  _bindUIEvents() {
    // 읽지 않은 채팅만 보기 토글
    this.toggleControl.addEventListener("change", async (e) => {
      const showOnly = e.target.checked;
      await this.loadList(showOnly);
    });

    this.listContainer.addEventListener("click", (e) => {
      const item = e.target.closest(SELECTORS.CHAT_ROOM_LIST_ITEM);
      if (!item) return;
      const roomId = Number(item.dataset.chatRoomId);
      this.store.setCurrentRoom(roomId);
    });

    // 동적으로 생성되는 방 클릭은 Renderer가 처리 후 store.setCurrentRoom() 호출
    // this.store.addEventListener("chatRoomsChanged", () => {
    //   this._attachClickHandlers();
    // });
  }

  _bindStoreEvents() {
    // 상태 변경 시 렌더러에게 전달
    this.store.addEventListener("chatRoomsChanged", (e) => {
      this.renderer._renderChatList(e.detail);
    });
  }

  async loadList(showUnreadOnly = false) {
    const userId = this.store.userId;
    if (!userId) return;

    try {
      const list = await this.api.getChatList({ userId, showUnreadOnly });
      this.store.setChatRooms(list, showUnreadOnly);
    } catch (err) {
      // error 콜백(ApiClient) 또는 Toast로 처리
    }
  }

  _autoSelectRoom() {
    const roomId = this.store.currentRoomId;
    if (roomId != null) {
      // 방법 A: selector로 바로 찾기
      const currentItem = this.listContainer.querySelector(`.list__room-list__item[data-chat-room-id="${roomId}"]`);

      if (currentItem) {
        currentItem.dispatchEvent(new MouseEvent("click", { bubbles: true }));
        return;
      }
    }
    const allItems = Array.from(this.listContainer.querySelectorAll(".list__room-list__item"));
    if (allItems.length === 0) return;

    // 챗봇 방 제외
    const normalItems = allItems.filter((item) => item.dataset.chatRoomId > 0);

    // 읽지 않은 채팅방 우선
    const unread = normalItems.find((item) => item.querySelector(".unread-badge"));

    // 읽지 않은 방 없으면 첫 번째 일반 방
    const target = unread || normalItems[0];

    if (target) {
      target.dispatchEvent(new MouseEvent("click", { bubbles: true }));
    }
  }

  _attachClickHandlers() {
    const items = document.querySelectorAll(SELECTORS.CHAT_ROOM_LIST_ITEM);
    items.forEach((item) => {
      item.onclick = () => {
        const roomId = Number(item.dataset.chatRoomId);
        this.store.setCurrentRoom(roomId);
      };
    });
  }
}
