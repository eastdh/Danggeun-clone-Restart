document.addEventListener("DOMContentLoaded", () => {
  // 채팅방 리스트에서 읽지 않은 메시지가 있는 방 찾기
  const chatRooms = Array.from(document.querySelectorAll(".list__room-list__item"));
  const unreadRooms = chatRooms.filter((room) => room.querySelector(".unread-badge"));

  // 읽지 않은 방이 있으면 첫 번째 선택, 없으면 전체 중 첫 번째 선택
  const targetRoom = unreadRooms[0] || chatRooms[0];
  if (targetRoom) {
    targetRoom.click();
  }

  // 메시지 전송 버튼 이벤트 연결
  const sendButton = document.querySelector(".room__input-area__send-button");
  if (sendButton) {
    sendButton.addEventListener("click", sendMessage);
  }

  const messageInput = document.querySelector(".room__input-area__field");

  messageInput.addEventListener("keydown", (event) => {
    if (event.key === "Enter") {
      if (event.shiftKey || event.ctrlKey) {
        // 줄바꿈 허용
        return;
      }

      // 기본 Enter 동작 방지 (줄바꿈 막기)
      event.preventDefault();

      // 메시지 전송 함수 호출
      sendMessage();
    }
  });
});

/**
 * 채팅방 선택 시 상세 정보 + 메시지 불러오기
 */
function selectRoom(chatRoomId) {
  const userId = document.querySelector(".list__header__user-id")?.dataset.userId;
  if (!userId) return;

  showLoading("채팅방을 불러오는 중입니다...");
  document.getElementById("roomSelected").style.display = "none";

  const roomElement = document.querySelector(`.list__room-list__item[onclick="selectRoom(${chatRoomId})"]`);

  // unread-badge가 있으면 읽음 처리 요청
  if (roomElement?.querySelector(".unread-badge")) {
    fetch("/api/chat/message/read", {
      method: "PATCH",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ chatRoomId, userId }),
    })
      .then(() => {
        // 읽음 처리 후 UI에서 뱃지 제거
        const badge = roomElement.querySelector(".unread-badge");
        if (badge) badge.remove();
        console.log("메시지 읽음 처리 완료");
      })
      .catch((error) => console.error("메시지 읽음 처리 실패:", error));
  }

  // 채팅방 상세 정보와 메시지 불러오기
  fetch(`/api/chat/room/${chatRoomId}`)
    .then((response) => response.json())
    .then((data) => {
      updateChatRoom(data.detail);
      updateMessages(data.messages);
      document.getElementById("roomEmptyMessage").style.display = "none";
      document.getElementById("roomSelected").style.display = "flex";
      hideLoading();
    })
    .then(() => {
      const container = document.querySelector(".room__messages");
      container.scrollTop = container.scrollHeight; // 최신 메시지로 스크롤
    })
    .catch((error) => {
      console.error("채팅방 로딩 실패:", error);
      hideLoading();
      document.getElementById("roomEmptyMessage").style.display = "block";
    });
}

/**
 * 거래 확정 요청
 */
function confirmTrade(chatRoomId, tradeId) {
  fetch("/api/chat/confirm-trade", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ tradeId }),
  })
    .then(() => {
      alert("거래가 확정되었습니다.");
      selectRoom(chatRoomId); // 채팅방 다시 로딩
    })
    .catch((error) => console.error("거래 확정 실패:", error));
}

/**
 * 읽지 않은 채팅만 보기 토글
 */
function toggleUnreadOnly(checked) {
  const userId = document.querySelector(".list__header__user-id")?.dataset.userId;
  if (!userId) return;

  fetch("/api/chat/list", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ userId, showUnreadOnly: checked }),
  })
    .then((response) => response.json())
    .then((chatRooms) => updateChatList(chatRooms))
    .catch((error) => console.error("채팅 리스트 로딩 실패:", error));
}

/**
 * 메시지 전송
 */
function sendMessage() {
  const chatRoomId = document.querySelector(".room__header__trade-info__status-button")?.dataset.chatRoomId;
  const senderId = document.querySelector(".list__header__user-id")?.dataset.userId;
  const input = document.querySelector(".room__input-area__field");
  const content = input?.value.trim();

  if (!chatRoomId || !senderId || !content) return;

  // Optimistic UI - 실제 메시지 전송 전에 임시 메시지 추가
  const tempMessage = {
    content,
    timestamp: new Date(),
    senderType: "ME",
    isRead: false,
    messageType: "TEXT",
    temp: true,
  };
  appendMessage(tempMessage);
  input.value = "";

  // 서버 전송
  fetch("/api/chat/message", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ chatRoomId, senderId, content }),
  })
    .then((res) => res.json())
    .then((msg) => {
      // 정확한 메시지로 교체 또는 삽입
      replaceOrInsertMessage(msg);
    });
}

/**
 * 채팅방 정보 업데이트
 */
function updateChatRoom(detail) {
  document.querySelector(".room__header__partner-info__id").textContent = detail.partnerNickname;
  document.querySelector(".room__header__partner-info__temperature").textContent = detail.partnerTemperature + "°C";

  document.querySelector(".room__header__trade-info__product__name").textContent = detail.tradeTitle;
  document.querySelector(".room__header__trade-info__product__price").textContent = detail.tradePrice + "원";

  const thumbnail = document.querySelector(".room__header__trade-info__product img.thumbnail");
  thumbnail.src = detail.tradeThumbnailUrl || "/assets/icon/default_product_img.svg";

  const statusButton = document.getElementById("tradeStatusButton");

  // 공통 속성 설정
  statusButton.setAttribute("data-chat-room-id", detail.chatRoomId);
  statusButton.setAttribute("data-trade-id", detail.tradeId);

  if (detail.closed) {
    // 거래 완료 상태
    statusButton.textContent = "거래 완료";
    statusButton.classList.add("closed");
    statusButton.disabled = true;
    statusButton.onclick = null;
  } else if (detail.seller) {
    // 판매자이고 거래 중일 때
    statusButton.textContent = "거래 확정하기";
    statusButton.classList.remove("closed");
    statusButton.disabled = false;
    statusButton.onclick = () => confirmTrade(detail.chatRoomId, detail.tradeId);
  } else {
    // 구매자이고 거래 중일 때
    statusButton.textContent = "거래 중";
    statusButton.classList.remove("closed");
    statusButton.disabled = true;
    statusButton.onclick = null;
  }
}

/**
 * 메시지 목록 업데이트
 */
function updateMessages(messages) {
  const container = document.querySelector(".room__messages");
  container.innerHTML = ""; // 기존 메시지 제거

  messages.forEach((msg) => {
    const wrapper = document.createElement("div");
    wrapper.classList.add("room__messages__item");

    if (msg.messageType === "DATE_LABEL") {
      wrapper.classList.add("room__messages__item--date");
      const label = document.createElement("div");
      label.classList.add("room__messages__item__date-label");
      label.textContent = msg.content;
      wrapper.appendChild(label);
    } else {
      const contentWrapper = document.createElement("div");
      contentWrapper.classList.add("room__messages__item__content");

      const text = document.createElement("div");
      text.classList.add("room__messages__item__text");
      text.textContent = msg.content || "메시지 내용";

      const meta = document.createElement("div");
      meta.classList.add("room__messages__item__meta");

      const time = document.createElement("div");
      time.classList.add("message-time");
      time.textContent = msg.timestamp || "";

      if (msg.senderType === "ME") {
        wrapper.classList.add("room__messages__item--me");
        contentWrapper.classList.add("room__messages__item__content");
        const readStatus = document.createElement("span");
        readStatus.classList.add("message-read-status");
        readStatus.textContent = msg.isRead ? "읽음" : "전송됨";
        meta.appendChild(readStatus);
      } else if (msg.senderType === "PARTNER") {
        wrapper.classList.add("room__messages__item--partner");
      } else if (msg.senderType === "CHAT_BOT") {
        wrapper.classList.add("room__messages__item--chat-bot");
      }

      meta.appendChild(time);
      contentWrapper.appendChild(text);
      contentWrapper.appendChild(meta);
      wrapper.appendChild(contentWrapper);
    }

    container.appendChild(wrapper);
  });
}

/**
 * 채팅방 리스트 업데이트
 */
function updateChatList(chatRooms) {
  const listContainer = document.querySelector(".list__room-list");
  listContainer.innerHTML = ""; // 기존 리스트 제거

  if (chatRooms.length === 0) {
    listContainer.innerHTML = `
      <div class="list__empty-message">
        <p class="empty-message__title">아직 시작된 채팅이 없어요</p>
        <p class="empty-message__subtitle">
          거래 상세 페이지에서 <strong>‘채팅하기’ 버튼</strong>을 누르면 <br />
          대화를 시작할 수 있어요.<br />
          관심 있는 상품을 먼저 둘러보는 건 어때요?
        </p>
        <a href="/trade" class="empty-message__browse-button">거래글 둘러보기</a>
      </div>
    `;
    return;
  }

  chatRooms.forEach((room) => {
    const item = document.createElement("div");
    item.classList.add("list__room-list__item");
    item.setAttribute("onclick", `selectRoom(${room.chatRoomId})`);

    const content = document.createElement("div");
    content.classList.add("list__room-list__item-content");

    const meta = document.createElement("div");
    meta.classList.add("list__room-list__item-meta");

    const nickname = document.createElement("span");
    nickname.classList.add("list__room-list__item-meta__partner-id");
    nickname.textContent = room.partnerNickname || "상대방";

    const location = document.createElement("div");
    location.classList.add("list__room-list__item-meta__location");
    location.textContent = room.partnerLocation;

    const time = document.createElement("div");
    time.classList.add("list__room-list__item-meta__timestamp");
    time.textContent = room.lastMessageTime;

    meta.appendChild(nickname);
    meta.appendChild(location);
    meta.appendChild(time);

    if (room.unreadCount > 0) {
      const badge = document.createElement("span");
      badge.classList.add("unread-badge");
      badge.textContent = room.unreadCount;
      meta.appendChild(badge);
    }

    const preview = document.createElement("div");
    preview.classList.add("list__room-list__item__preview");
    preview.textContent = room.lastMessageContent || "메시지 미리보기";

    content.appendChild(meta);
    content.appendChild(preview);

    const thumbnail = document.createElement("img");
    thumbnail.classList.add("thumbnail");
    thumbnail.src = room.tradeThumbnailUrl || "/assets/icon/default_product_img.svg";

    item.appendChild(content);
    item.appendChild(thumbnail);

    listContainer.appendChild(item);
  });
}

/**
 * 메시지 추가
 */
function appendMessage(msg, container = document.querySelector(".room__messages")) {
  const wrapper = document.createElement("div");
  wrapper.classList.add("room__messages__item");

  if (msg.temp) wrapper.classList.add("temp-message");
  if (msg.id) wrapper.setAttribute("data-message-id", msg.id);

  if (msg.messageType === "DATE_LABEL") {
    wrapper.classList.add("room__messages__item--date");
    const label = document.createElement("div");
    label.classList.add("room__messages__item__date-label");
    label.textContent = msg.content;
    wrapper.appendChild(label);
  } else {
    const contentWrapper = document.createElement("div");
    contentWrapper.classList.add("room__messages__item__content");

    const text = document.createElement("div");
    text.classList.add("room__messages__item__text");
    text.textContent = msg.content || "메시지 내용";

    const meta = document.createElement("div");
    meta.classList.add("room__messages__item__meta");

    const time = document.createElement("div");
    time.classList.add("message-time");
    time.textContent = formatTime(msg.timestamp) || "";

    if (msg.senderType === "ME") {
      wrapper.classList.add("room__messages__item--me");
      const readStatus = document.createElement("span");
      readStatus.classList.add("message-read-status");
      readStatus.textContent = msg.isRead ? "읽음" : "전송됨";
      meta.appendChild(readStatus);
    } else if (msg.senderType === "PARTNER") {
      wrapper.classList.add("room__messages__item--partner");
    } else if (msg.senderType === "CHAT_BOT") {
      wrapper.classList.add("room__messages__item--chat-bot");
    }

    meta.appendChild(time);
    contentWrapper.appendChild(text);
    contentWrapper.appendChild(meta);
    wrapper.appendChild(contentWrapper);
  }

  container.appendChild(wrapper);
  wrapper.scrollIntoView({ behavior: "smooth" });
}

function replaceOrInsertMessage(msg) {
  const container = document.querySelector(".room__messages");

  // temp 메시지 교체
  const temp = container.querySelector(".temp-message");
  if (temp) {
    temp.replaceWith(createMessageElement(msg));
    return;
  }

  // temp 메시지가 없으면 정렬 기준으로 삽입
  const newElement = createMessageElement(msg);
  const timestamp = new Date(msg.timestamp).getTime();

  const items = Array.from(container.querySelectorAll(".room__messages__item"))
    .filter((el) => el.dataset.messageId)
    .sort((a, b) => {
      const ta = new Date(a.querySelector(".message-time")?.textContent).getTime();
      const tb = new Date(b.querySelector(".message-time")?.textContent).getTime();
      return ta - tb;
    });

  let inserted = false;
  for (const item of items) {
    const itemTime = new Date(item.querySelector(".message-time")?.textContent).getTime();
    if (timestamp < itemTime) {
      container.insertBefore(newElement, item);
      inserted = true;
      break;
    }
  }

  if (!inserted) container.appendChild(newElement);
  container.scrollTop = container.scrollHeight;
}

function formatTime(raw) {
  if (!raw) return "시간 정보 없음";

  const date = new Date(raw);
  if (isNaN(date.getTime())) {
    return raw;
  }

  const hours = date.getHours();
  const minutes = date.getMinutes().toString().padStart(2, "0");
  const isPM = hours >= 12;
  const hour12 = hours % 12 || 12;

  return `${isPM ? "오후" : "오전"} ${hour12}:${minutes}`;
}

function createMessageElement(msg) {
  // 기존 appendMessage 로직을 재사용 가능하게 분리
  const dummyContainer = document.createElement("div");
  appendMessage(msg, dummyContainer);
  return dummyContainer.firstChild;
}

function showLoading(message = "로딩 중입니다...") {
  const indicator = document.getElementById("loadingIndicator");
  indicator.querySelector(".loading-message").textContent = message;
  indicator.style.display = "flex";
}

function hideLoading() {
  const indicator = document.getElementById("loadingIndicator");
  indicator.style.display = "none";
}
