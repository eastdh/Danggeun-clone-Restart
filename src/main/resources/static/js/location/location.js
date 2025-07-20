let map;
let geocoder;

function initMap() {
    const defaultLocation = { lat: 37.5665, lng: 126.9780 }; // 서울 중심 좌표

    map = new google.maps.Map(document.getElementById("map-container"), {
        zoom: 15,
        center: defaultLocation,
    });

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const userLocation = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude,
                };
                map.setCenter(userLocation);

                new google.maps.Marker({
                    position: userLocation,
                    map: map,
                });

                const geocoder = new google.maps.Geocoder();
                geocoder.geocode({ location: userLocation }, (results, status) => {
                    if (status === "OK" && results[0]) {
                        const components = results[0].address_components;

                        let province = "";  // 도 or 특별시/광역시
                        let city = "";      // 시
                        let district = "";  // 구

                        for (const comp of components) {
                            if (comp.types.includes("administrative_area_level_1")) {
                                province = comp.long_name;  // 서울특별시, 경기도
                            }
                            if (comp.types.includes("locality") || comp.types.includes("administrative_area_level_2")) {
                                city = comp.long_name;      // 용인시, 수원시, 부산광역시의 구
                            }
                            if (comp.types.includes("sublocality") || comp.types.includes("administrative_area_level_3")) {
                                district = comp.long_name;  // 수지구, 마포구 등
                            }
                        }

                        let locationText;
                        if (province.includes("특별시") || province.includes("광역시")) {
                            // 서울/부산 같은 광역시는 시+구까지만
                            locationText = `${province} ${district}`;
                        } else {
                            // 도 지역은 도 + 시 + 구까지
                            locationText = `${province} ${city} ${district}`;
                        }

                        const locationElem = document.getElementById("current-location");
                        if (locationElem) {
                            locationElem.textContent = locationText;
                        }
                    }
                });
            },
            () => alert("현재 위치를 가져올 수 없습니다.")
        );
    } else {
        alert("이 브라우저는 위치 서비스를 지원하지 않습니다.");
    }
}
document.addEventListener("DOMContentLoaded", () => {
    const confirmBtn = document.querySelector(".btn-confirm");
    const descriptionEl = document.querySelector(".location-description");

    confirmBtn.addEventListener("click", () => {
        const text = descriptionEl.textContent;

        // "현재 위치는 OOO 입니다." → OOO 추출
        const match = text.match(/현재 위치는\s(.+)\s입니다/);
        const location = match ? match[1] : null;

        if (!location) {
            alert("위치를 확인할 수 없습니다.");
            return;
        }

        // 서버로 위치 전송
        fetch("/location/update", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `location=${encodeURIComponent(location)}`
        })
            .then(res => res.text())
            .then(result => {
                if (result === "SUCCESS") {
                    alert("동네 인증이 완료되었습니다!");
                    window.location.href = "/"; // 메인으로 이동
                } else {
                    alert("로그인이 필요합니다.");
                    window.location.href = "/login";
                }
            })
            .catch(err => {
                console.error(err);
                alert("서버 오류가 발생했습니다.");
            });
    });
});