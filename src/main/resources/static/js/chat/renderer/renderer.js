// resources/static/js/chat/renderer/renderer.js
import { SELECTORS, MESSAGE_TYPES, SENDER_TYPES } from "../constants.js";
import { formatKoreanTime, isKoreanTimeString } from "../utils/time_formatter.js";

export class Renderer {
  constructor(store) {
    this.store = store;
    this._cacheElements();
    this._bindStoreEvents();
  }

  // 1) DOM 요소 캐싱
  _cacheElements() {
    this.listContainer = document.querySelector(".list__room-list");
    this.emptyListEl = document.querySelector(".list__empty-message");
    this.roomSection = document.getElementById("roomSelected");
    this.roomEmptyEl = document.getElementById("roomEmptyMessage");
    this.loadingEl = document.getElementById("loadingIndicator");
    this.msgContainer = document.querySelector(SELECTORS.MESSAGE_CONTAINER);
    this.sendButton = document.querySelector(SELECTORS.SEND_BUTTON);
    this.inputField = document.querySelector(SELECTORS.MESSAGE_INPUT);
    this.tradeStatusBtn = document.querySelector(SELECTORS.TRADE_STATUS_BUTTON);
    this.partnerIdEl = document.querySelector(".room__header__partner-info__id");
    this.partnerTempEl = document.querySelector(".room__header__partner-info__temperature");
    this.tradeNameEl = document.querySelector(".room__header__trade-info__product__name");
    this.tradePriceEl = document.querySelector(".room__header__trade-info__product__price");
    this.tradeImgEl = document.querySelector(".room__header__trade-info__product img.thumbnail");
  }

  // 2) 스토어 이벤트 구독
  _bindStoreEvents() {
    this.store.addEventListener("chatRoomsChanged", (e) => this._renderChatList(e.detail));
    this.store.addEventListener("chatRoomUpdated", (e) => this._updateChatRoomSummary(e.detail));
    this.store.addEventListener("currentRoomChanged", () => this._showRoomSection());
    this.store.addEventListener("roomDetailChanged", (e) => this._renderRoomDetail(e.detail));
    this.store.addEventListener("messagesChanged", (e) => this._renderMessages(e.detail));
    this.store.addEventListener("messageAppended", (e) => this._appendMessage(e.detail));
    this.store.addEventListener("messagesRead", (e) => this._markMessagesRead(e.detail));
  }

  // 3) 채팅방 리스트 렌더링
  _renderChatList({ list, showUnreadOnly }) {
    this.listContainer.innerHTML = "";
    // ① 챗봇 방 고정 추가
    const botItem = document.createElement("div");
    botItem.className = "list__room-list__item list__room-list__item--bot";
    botItem.dataset.chatRoomId = -this.store.userId;
    botItem.innerHTML = `
    <div class="list__room-list__item-content">
      <span class="chat-list__item-icon">🤖</span>
      <span class="list__room-list__item__preview">챗봇</span>
    </div>
  `;
    this.listContainer.appendChild(botItem);

    if (list.length === 0) {
      this.emptyListEl.style.display = "block";
      return;
    }
    this.emptyListEl.style.display = "none";

    list.forEach((room) => {
      const item = document.createElement("div");
      const thumbUrl = room.tradeThumbnailUrl ? room.tradeThumbnailUrl : "/assets/icon/default_product_img.svg";
      item.className = "list__room-list__item";
      item.dataset.chatRoomId = room.chatRoomId;
      item.innerHTML = `
        <div class="list__room-list__item-content">
          <div class="list__room-list__item-meta">
            <span class="list__room-list__item-meta__partner-id">${room.partnerNickname}</span>
            <div class="list__room-list__item-meta__location">${room.partnerLocation}</div>
            <div class="list__room-list__item-meta__timestamp">${room.lastMessageTime}</div>
            ${room.unreadCount > 0 ? `<span class="unread-badge">${room.unreadCount}</span>` : ""}
          </div>
          <div class="list__room-list__item__preview">${room.lastMessageContent}</div>
        </div>
        <img class="thumbnail" src="${thumbUrl}"/>
      `;
      this.listContainer.appendChild(item);
    });
  }

  // 4) 특정 방 요약 업데이트
  _updateChatRoomSummary({ summary, isNew }) {
    const selector = `.list__room-list__item[data-chat-room-id="${summary.chatRoomId}"]`;
    let item = document.querySelector(selector);

    if (!item) {
      // 새 아이템 삽입
      this._renderChatList({ list: [summary], showUnreadOnly: false });
      return;
    }

    // 기존 아이템 업데이트
    item.querySelector(".list__room-list__item__preview").textContent = summary.lastMessageContent;
    item.querySelector(".list__room-list__item-meta__timestamp").textContent = summary.lastMessageTime;

    const badge = item.querySelector(".unread-badge");
    if (summary.unreadCount > 0) {
      if (badge) badge.textContent = summary.unreadCount;
      else item.querySelector(".list__room-list__item-meta").insertAdjacentHTML("beforeend", `<span class="unread-badge">${summary.unreadCount}</span>`);
    } else if (badge) {
      badge.remove();
    }

    // 상단 이동
    if (isNew && item.parentNode) {
      item.parentNode.prepend(item.parentNode.querySelector(".list__room-list__item--bot"), item);
    }
  }

  // 5) 방 선택 UI 전환
  _showRoomSection() {
    this.roomEmptyEl.style.display = "none";
    this.roomSection.style.display = "flex";
  }

  // 6) 채팅방 헤더(상대·거래) 렌더링
  _renderRoomDetail(detail) {
    if (detail.chatRoomId < 0) {
      // 1) 상대 정보
      this.partnerIdEl.textContent = detail.partnerNickname;
      this.partnerTempEl.style.display = "none";

      // 2) 거래 정보 숨기기
      document.querySelector(".room__header__trade-info").style.display = "none";

      return;
    }

    this.partnerIdEl.textContent = detail.partnerNickname;
    this.partnerTempEl.textContent = detail.partnerTemperature + "°C";
    document.querySelector(".room__header__trade-info").style.display = "flex";
    this.tradeNameEl.textContent = detail.tradeTitle;
    this.tradePriceEl.textContent = detail.tradePrice + "원";
    this.tradeImgEl.src = detail.tradeThumbnailUrl || "/assets/icon/default_product_img.svg";

    // 거래 상태 버튼
    this.tradeStatusBtn.dataset.chatRoomId = detail.chatRoomId;
    this.tradeStatusBtn.dataset.tradeId = detail.tradeId;
    if (detail.closed) {
      this.tradeStatusBtn.textContent = "거래 완료";
      this.tradeStatusBtn.classList.add("closed");
      this.tradeStatusBtn.disabled = true;
    } else if (detail.seller) {
      this.tradeStatusBtn.textContent = "거래 확정하기";
      this.tradeStatusBtn.classList.remove("closed");
      this.tradeStatusBtn.disabled = false;
    } else {
      this.tradeStatusBtn.textContent = "거래 중";
      this.tradeStatusBtn.disabled = true;
    }
  }

  /**
   * 거래 확정(완료) 버튼 상태를 토글
   * @param {boolean} isCompleted 거래가 확정되어 버튼을 비활성화할지 여부
   */
  updateTradeStatusButton(isCompleted) {
    const btn = this.tradeStatusBtn;
    if (!btn) return;

    if (isCompleted) {
      btn.textContent = "거래 완료";
      btn.disabled = true;
      btn.classList.add("closed");
    } else {
      // 판매자용 “거래 확정하기” 또는 구매자용 “거래 중”은
      // _renderRoomDetail 로직을 다시 호출하거나 detail 객체를 전달해서 복원
      const { closed, seller } = this.currentRoomDetail || {};
      if (seller) {
        btn.textContent = "거래 확정하기";
        btn.disabled = false;
        btn.classList.remove("closed");
      } else {
        btn.textContent = "거래 중";
        btn.disabled = true;
      }
    }
  }

  // 7) 메시지 리스트 렌더링
  _renderMessages(messages) {
    this.msgContainer.innerHTML = "";
    if (messages.length === 0) return;
    messages.forEach((msg) => this._appendMessage(msg));
  }

  removeTempMessage() {
    const tempEl = this.msgContainer.querySelector(".temp-message");
    if (tempEl) {
      tempEl.remove();
    }
  }

  // 8) 메시지 한 건 추가
  _appendMessage(msg) {
    if (msg.messageType === MESSAGE_TYPES.SYSTEM) {
      this.appendSystemMessage(msg.content, {
        buttonText: msg.buttonText,
        buttonUrl: msg.buttonUrl,
      });
      return;
    }

    const wrapper = document.createElement("div");
    wrapper.className = "room__messages__item";

    // temp 메시지라면 class 추가
    if (msg.tempMessage) {
      wrapper.classList.add("temp-message");
    } else {
      wrapper.dataset.messageId = msg.messageId;
    }

    // 날짜 라벨
    if (msg.messageType === MESSAGE_TYPES.DATE_LABEL) {
      wrapper.classList.add("room__messages__item--date");
      wrapper.innerHTML = `<div class="room__messages__item__date-label">${msg.content}</div>`;
      this.msgContainer.appendChild(wrapper);
      return;
    }

    const contentWrapper = document.createElement("div");
    contentWrapper.classList.add("room__messages__item__content");

    const text = document.createElement("div");
    text.classList.add("room__messages__item__text");
    text.textContent = msg.content;

    const meta = document.createElement("div");
    meta.classList.add("room__messages__item__meta");

    const timeEl = document.createElement("div");
    timeEl.classList.add("message-time");
    const rawTime = msg.timestamp;
    if (isKoreanTimeString(rawTime)) {
      timeEl.textContent = rawTime; // 이미 한국어 시간 형식
    } else {
      timeEl.textContent = formatKoreanTime(new Date(rawTime));
    }

    const isMe = msg.senderId === this.store.userId;
    if (isMe) {
      wrapper.classList.add("room__messages__item--me");
      const readStatus = document.createElement("span");
      readStatus.classList.add("message-read-status");
      readStatus.textContent = msg.isRead ? "읽음" : "전송됨";
      meta.appendChild(readStatus);
    } else if (msg.messageType === MESSAGE_TYPES.DATE_LABEL) {
      wrapper.classList.add("room__messages__item__date-label");
    } else if (msg.messageType === MESSAGE_TYPES.CHAT_BOT) {
      wrapper.classList.add("room__messages__item--chat-bot");
    } else {
      wrapper.classList.add("room__messages__item--partner");
    }

    meta.appendChild(timeEl);
    contentWrapper.appendChild(text);
    contentWrapper.appendChild(meta);
    wrapper.appendChild(contentWrapper);

    this.msgContainer.appendChild(wrapper);
    wrapper.scrollIntoView({ behavior: "smooth" });
  }

  appendSystemMessage(content, options = {}) {
    const { buttonText = "", buttonUrl = "" } = options;

    const wrapper = document.createElement("div");
    wrapper.className = "room__messages__item room__messages__item--system";

    // 메시지 본문 + 버튼(옵션)
    wrapper.innerHTML = `
      <div class="room__messages__item__content system">
        ${content}
        ${buttonText ? `<button class="system-action">${buttonText}</button>` : ""}
      </div>
    `;

    this.msgContainer.appendChild(wrapper);
    wrapper.scrollIntoView({ behavior: "smooth" });

    // 버튼 클릭 핸들러
    if (buttonText && buttonUrl) {
      const btn = wrapper.querySelector(".system-action");
      console.log("[Renderer] 시스템 메시지 버튼 클릭", buttonUrl);
      btn.addEventListener("click", () => {
        // 후기 작성 버튼 클릭 이벤트

        const params = new URLSearchParams(buttonUrl.split("?")[1]);
        const tradeId = params.get("trade_id");
        const sellerId = Number(params.get("seller_id"));
        const buyerId = Number(params.get("buyer_id"));
        const chatRoomId = params.get("chat_room_id");

        // 현재 로그인 유저 ID는 store.userId
        const isSeller = this.store.userId === sellerId;
        const partnerId = isSeller ? buyerId : sellerId;

        window.location.href = `/review/write` + `?trade_id=${tradeId}` + `&partner_id=${partnerId}` + `&is_seller=${isSeller}` + `&chat_room_id=${chatRoomId}`;
      });
    }
  }

  // 9) 읽음 상태 UI 업데이트
  _markMessagesRead(messageIds) {
    messageIds.forEach((id) => {
      const selector = `.room__messages__item[data-message-id="${id}"] .message-read-status`;
      const el = this.msgContainer.querySelector(selector);
      if (el) {
        el.textContent = "읽음";
      }
    });
  }

  showLoading(msg) {
    this.loadingEl.querySelector(".loading-message").textContent = msg;
    this.loadingEl.style.display = "flex";
  }

  hideLoading() {
    this.loadingEl.style.display = "none";
  }

  showRoom() {
    this.roomEmptyEl.style.display = "none";
    this.roomSection.style.display = "flex";
    this.msgContainer.scrollTop = this.msgContainer.scrollHeight;
  }

  hideRoom() {
    this.roomSection.style.display = "none";
  }

  showEmptyRoom() {
    this.roomSection.style.display = "none";
    this.roomEmptyEl.style.display = "block";
  }
}
