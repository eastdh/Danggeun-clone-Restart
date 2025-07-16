document.addEventListener("DOMContentLoaded", function () {
    const filterButtons = document.querySelectorAll(".tab-button");  // HTML과 일치
    const productCards = document.querySelectorAll(".item-card");    // HTML과 일치

    filterButtons.forEach((btn) => {
        btn.addEventListener("click", function () {
            const status = btn.getAttribute("data-status");

            // 모든 버튼 active 제거
            filterButtons.forEach((b) => b.classList.remove("active"));
            // 클릭한 버튼만 active 부여
            btn.classList.add("active");

            // 카드 필터링
            productCards.forEach((card) => {
                const cardStatus = card.getAttribute("data-status");

                if (status === "ALL" || cardStatus === status) {
                    card.style.display = "block";
                } else {
                    card.style.display = "none";
                }
            });
        });
    });
    const editBtn = document.querySelector("a[href='/profile/edit']");
    const modal = document.getElementById("profile-edit-modal");
    const cancelBtn = document.getElementById("cancel-btn");
    const form = document.getElementById("profile-edit-form");

    // 버튼 클릭 시 모달 열기
    if (editBtn) {
        editBtn.addEventListener("click", function (e) {
            e.preventDefault();
            modal.classList.remove("hidden");
        });
    }

    // 취소 버튼
    cancelBtn.addEventListener("click", function () {
        modal.classList.add("hidden");
    });

    // 폼 제출 처리
    form.addEventListener("submit", function (e) {
        e.preventDefault();
        const nickname = document.getElementById("nickname").value;

        fetch("/api/profile/nickname", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ nickname: nickname })
        })
            .then(res => {
                if (res.ok) return res.json();
                else throw new Error("닉네임 저장 실패");
            })
            .then(data => {
                alert("닉네임이 변경되었습니다.");
                location.reload();
            })
            .catch(err => {
                alert("오류 발생: " + err.message);
            });
    });
});