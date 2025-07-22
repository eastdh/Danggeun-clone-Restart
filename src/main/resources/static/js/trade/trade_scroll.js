const url = new URL(window.location.href);
let page = url.searchParams.get("page") || 1;
const keyword = url.searchParams.get("keyword");
const location = url.searchParams.get("location");
const status = url.searchParams.get("status");
const category = url.searchParams.get("category");
const priceRange = url.searchParams.get("priceRange");

let loadingLock = false;
let lastContent = false;

const loadingBox = document.querySelector(".loading");

function fetchMoreTrade() {
  if (loadingLock) return;
  if (lastContent) return;
  loadingLock = true;
  loadingBox.classList.remove("hidden");

  const params = new URLSearchParams();
  params.append("page", page);
  if (keyword) params.append("keyword", keyword);
  if (location) params.append("location", location);
  if (status) params.append("status", status);
  if (category) params.append("category", category);
  if (priceRange) params.append("priceRange", priceRange);

  const requestUrl = `/api/trade?${params.toString()}`;
  fetch(requestUrl, {
    method: "GET",
    headers: {
      Accept: "application/json",
    },
  })
    .then((res) => res.json())
    .then((data) => {
      lastContent = data.empty;
      loadingBox.classList.add("hidden");
      if (lastContent) return;

      const trades = data.content;
      const tradeContents = document.querySelector(".trade-contents");
      const cards = trades.map(buildCard);
      cards.forEach((card) => tradeContents.appendChild(card));
      loadingLock = false;
      page++;
    })
    .catch(() => {
      loadingBox.classList.add("hidden");
      loadingLock = false;
    });
}

function buildCard(trade) {
  // card
  const card = document.createElement("div");
  card.className = "trade-card";

  // 링크
  const link = document.createElement("a");
  link.className = "trade-card-link";
  link.href = `/trade/${trade.tradeId}`;

  // 이미지 박스
  const imgBox = document.createElement("div");
  imgBox.className = "card-img-box";

  // 상태 뱃지
  if (trade.status === "completed") {
    const badge = document.createElement("span");
    badge.className = "badge-status completed";
    badge.textContent = "판매완료";
    imgBox.appendChild(badge);
  } else if (trade.status === "reserved") {
    const badge = document.createElement("span");
    badge.className = "badge-status reserved";
    badge.textContent = "예약중";
    imgBox.appendChild(badge);
  }

  // 이미지
  const img = document.createElement("img");
  img.className = "card-img";
  img.src =
    !trade.imgUrl || trade.imgUrl.trim() === ""
      ? "/assets/icon/card-image.svg"
      : trade.imgUrl;
  img.alt = trade.title ?? "거래글 이미지";
  imgBox.appendChild(img);
  link.appendChild(imgBox);

  // 정보 박스
  const infoBox = document.createElement("div");
  infoBox.className = "card-info";

  // 제목 & 가격
  const titlePriceBox = document.createElement("div");

  const titleSpan = document.createElement("span");
  titleSpan.className = "card-title";
  titleSpan.textContent = trade.title;

  const priceSpan = document.createElement("span");
  priceSpan.className = "card-price";
  priceSpan.textContent = formatPrice(trade.price) + "원";

  titlePriceBox.appendChild(titleSpan);
  titlePriceBox.appendChild(priceSpan);
  infoBox.appendChild(titlePriceBox);

  // 위치 & 시간
  const locationTimeBox = document.createElement("div");

  const locationSpan = document.createElement("span");
  locationSpan.textContent = trade.location;
  locationTimeBox.appendChild(locationSpan);

  const timeWrap = document.createElement("span");
  timeWrap.className = "card-time";

  const dot = document.createElement("span");
  dot.textContent = "•";
  timeWrap.appendChild(dot);

  const timeText = document.createElement("span");

  if (!trade.bumpUpdatedAt) {
    timeText.textContent = trade.updateTerm;
  } else {
    const isBumpAfter =
      new Date(trade.bumpUpdatedAt) > new Date(trade.updatedAt);
    timeText.textContent = isBumpAfter
      ? `끌올 ${trade.bumpUpdateTerm}`
      : trade.updateTerm;
  }

  timeWrap.appendChild(timeText);
  locationTimeBox.appendChild(timeWrap);

  infoBox.appendChild(locationTimeBox);
  link.appendChild(infoBox);
  card.appendChild(link);

  return card;
}

function formatPrice(price) {
  return price.toLocaleString("ko-KR");
}

let lastScrollY = window.screenY;

function checkScroll() {
  const scrollY = window.scrollY || window.pageYOffset;
  const windowInnerHeight = window.innerHeight;
  const direction = scrollY > lastScrollY ? "down" : "up";
  lastScrollY = scrollY;

  if (direction === "up") return;
  else {
    const contentBox = document.querySelector(".trade-contents");
    const contentHeight = contentBox.offsetHeight;
    if (scrollY + windowInnerHeight >= contentHeight) {
      fetchMoreTrade();
    }
  }
}

window.addEventListener("scroll", checkScroll);
