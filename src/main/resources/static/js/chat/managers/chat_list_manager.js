// resources/static/js/chat/managers/chat_list_manager.js
import { BOT_ROOM_ID, SELECTORS } from "../constants.js";

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
    const items = Array.from(this.listContainer.querySelectorAll(".list__room-list__item"));
    if (!items.length) return;

    // 읽지 않은 방 우선
    const unread = items.find((item) => item.querySelector(".unread-badge"));
    const target = unread || items[1]; // 챗봇 방은 제외하고 두 번째 방 선택
    // 클릭 이벤트 위임에 걸리도록 dispatch
    target.dispatchEvent(new MouseEvent("click", { bubbles: true }));
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
