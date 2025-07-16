package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.repository.UserRepository;
import io.github.restart.gmo_danggeun.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class OAuth2Controller {

    private final UserRepository userRepository;

    // 닉네임 입력 폼 GET
    @GetMapping("/oauth2/nickname")
    public String nicknameForm(HttpSession session, Model model) {
        String email = (String) session.getAttribute("oauth2_email");

        if (email == null) {
            return "redirect:/login";
        }

        model.addAttribute("email", email);
        return "login/oauth2_nickname";
    }

    // 닉네임 입력 폼 POST
    @PostMapping("/oauth2/nickname")
    public String submitNickname(@RequestParam String nickname, HttpSession session, HttpServletRequest request) {
        String email = (String) session.getAttribute("oauth2_email");

        if (email == null || nickname.isBlank()) {
            return "redirect:/login";
        }

        // ✅ 닉네임 중복 체크
        if (userRepository.existsByNickname(nickname)) {
            session.setAttribute("nickname_error", "이미 사용 중인 닉네임입니다.");
            return "redirect:/oauth2/nickname";
        }

        // ✅ 새 유저 저장
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setNickname(nickname);
        newUser.setPassword("OAUTH2");
        newUser.setJoinDate(LocalDateTime.now());
        newUser.setMannerScore(BigDecimal.valueOf(36.5));
        newUser.setLocation("");

        userRepository.save(newUser);
        session.removeAttribute("oauth2_email");

        // ✅ 자동 로그인 처리
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                new CustomUserDetails(newUser), null, new CustomUserDetails(newUser).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/"; // 메인 페이지 등으로 이동
    }
}