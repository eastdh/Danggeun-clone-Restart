document.addEventListener("DOMContentLoaded", function () {
    /*** 1) 판매상품/관심목록 필터 버튼 처리 ***/
    const filterButtons = document.querySelectorAll(".tab-button");
    const productCards = document.querySelectorAll(".item-card");

    filterButtons.forEach((btn) => {
        btn.addEventListener("click", function () {
            const status = btn.getAttribute("data-status");
            filterButtons.forEach((b) => b.classList.remove("active"));
            btn.classList.add("active");

            productCards.forEach((card) => {
                const cardStatus = card.getAttribute("data-status");
                card.style.display =
                    status === "ALL" || cardStatus === status ? "block" : "none";
            });
        });
    });

    /*** 2) 상품 카드 클릭 시 상세 페이지 이동 ***/
    document
        .querySelectorAll(".sale-items .item-card, .liked-items .item-card")
        .forEach((card) => {
            card.addEventListener("click", function () {
                const tradeId = card.getAttribute("data-id");
                if (!tradeId) {
                    console.warn("이 카드에는 tradeId가 없습니다.");
                    return;
                }
                window.location.href = `/trade/${tradeId}`;
            });
        });

    /*** 3) 프로필 수정 모달 처리 ***/
    const editBtn = document.querySelector("a[href='/profile/edit']");
    const modal = document.getElementById("profile-edit-modal");
    const cancelBtn = document.getElementById("cancel-btn");
    const form = document.getElementById("profile-edit-form");

    if (editBtn) {
        editBtn.addEventListener("click", function (e) {
            e.preventDefault();
            modal.classList.remove("hidden");
        });
    }
    if (cancelBtn) {
        cancelBtn.addEventListener("click", function () {
            modal.classList.add("hidden");
        });
    }
    if (form) {
        form.addEventListener("submit", function (e) {
            e.preventDefault();
            const nickname = document.getElementById("nickname").value;

            fetch("/api/profile/nickname", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ nickname: nickname }),
            })
                .then((res) => {
                    if (res.ok) return res.json();
                    else throw new Error("닉네임 저장 실패");
                })
                .then(() => {
                    alert("닉네임이 변경되었습니다.");
                    location.reload();
                })
                .catch((err) => {
                    alert("오류 발생: " + err.message);
                });
        });
    }

    /*** 4) "더보기" 애니메이션 (판매상품/관심목록/후기) ***/
    function setupShowMore(containerId, btnId, visibleCount) {
        const container = document.getElementById(containerId);
        const btn = document.getElementById(btnId);
        if (!container || !btn) return;

        const items = container.querySelectorAll(":scope > *");

        // 기본적으로 visibleCount까지만 보이게 max-height 설정
        const itemHeight = items[0]?.offsetHeight || 150;
        const collapsedHeight = itemHeight * visibleCount;
        container.style.maxHeight = collapsedHeight + "px";
        container.style.overflow = "hidden";
        container.style.transition = "max-height 0.4s ease";

        if (items.length > visibleCount) {
            btn.style.display = "block";
        }

        btn.addEventListener("click", function () {
            const expanded = container.classList.toggle("expanded");

            if (expanded) {
                container.style.maxHeight = container.scrollHeight + "px";
                btn.textContent = "접기";
            } else {
                container.style.maxHeight = collapsedHeight + "px";
                btn.textContent = "더보기";
            }
        });
    }

    // 적용 (2줄=8개 기준, 후기는 3개 기준)
    setupShowMore("sale-items-grid", "show-more-sale-btn", 8);
    setupShowMore("liked-items-grid", "show-more-liked-btn", 8);
    setupShowMore("review-list", "show-more-review-btn", 3);
});
