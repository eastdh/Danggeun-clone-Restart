package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.repository.UserRepository;
import io.github.restart.gmo_danggeun.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LocationController {

    private final UserRepository userRepository;

    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    // 동네 인증 페이지 표시
    @GetMapping("/location")
    public String locationPage(Model model) {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "location/location";  // templates/location/location.html
    }

    // 동네 인증 완료 시 현재 위치 DB에 저장
    @PostMapping("/location/update")
    @ResponseBody
    public String updateLocation(@RequestParam String location) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 로그인 여부 확인
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            return "NOT_LOGGED_IN";
        }

        // 현재 로그인한 유저 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        // 위치 업데이트
        user.setLocation(location);
        userRepository.save(user);

        return "SUCCESS";
    }
}