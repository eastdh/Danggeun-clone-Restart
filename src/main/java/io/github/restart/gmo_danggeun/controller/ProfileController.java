package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.dto.auth.ProfileUpdateDto;
import io.github.restart.gmo_danggeun.dto.auth.ProfileViewDto;
import io.github.restart.gmo_danggeun.repository.UserRepository;
import io.github.restart.gmo_danggeun.security.CustomUserDetails;
import io.github.restart.gmo_danggeun.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;
    private final ProfileService profileService;

    @GetMapping("/profile")
    public String myProfilePage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();

        ProfileViewDto profileData = profileService.getProfileDataByUserId(userId);
        model.addAttribute("profileData", profileData);
        model.addAttribute("currentUserId", userId); // ✅ 추가

        return "profile/profile";
    }

    @GetMapping("/profile/{id}")
    public String otherUserProfilePage(@PathVariable Long id, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (!userRepository.existsById(id)) {
            return "error/404";
        }

        ProfileViewDto profileData = profileService.getProfileDataByUserId(id);
        model.addAttribute("profileData", profileData);
        if (userDetails != null) {
            model.addAttribute("currentUserId", userDetails.getUser().getId()); // ✅ 추가
        }

        return "profile/profile";
    }
    @PostMapping("/api/profile/nickname")
    @ResponseBody
    public ResponseEntity<?> updateNickname(@RequestBody ProfileUpdateDto dto,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            profileService.updateNickname(userDetails.getUser().getId(), dto.getNickname());
            return ResponseEntity.ok().body(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("닉네임 저장 실패: " + e.getMessage());
        }
    }
    @GetMapping("/api/profile/nickname/check")
    @ResponseBody
    public ResponseEntity<?> checkNickname(@RequestParam String nickname) {
        boolean duplicated = profileService.isNicknameDuplicated(nickname);
        return ResponseEntity.ok(duplicated);
    }
}