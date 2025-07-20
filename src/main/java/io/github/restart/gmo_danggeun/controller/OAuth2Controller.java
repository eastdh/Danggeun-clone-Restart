package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.repository.UserRepository;
import io.github.restart.gmo_danggeun.security.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class OAuth2Controller {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/oauth2/nickname")
    public String nicknameForm(HttpSession session, org.springframework.ui.Model model) {
        String email = (String) session.getAttribute("oauth2_email");
        if (email == null) {
            return "redirect:/login";
        }
        model.addAttribute("email", email);
        return "login/oauth2_nickname";
    }

    @PostMapping("/oauth2/nickname")
    public String submitNickname(@RequestParam String nickname, HttpSession session, Model model) {
        String email = (String) session.getAttribute("oauth2_email");

        if (email == null) {
            return "redirect:/login";
        }

        // 이메일 중복 체크
        if (userRepository.existsByEmail(email)) {
            return "redirect:/login?error=duplicate";
        }

        // 닉네임 유효성 검사
        if (nickname.isBlank()) {
            model.addAttribute("error", "닉네임을 입력해주세요.");
            model.addAttribute("email", email);
            return "login/oauth2_nickname";
        }

        if (userRepository.existsByNickname(nickname)) {
            model.addAttribute("error", "이미 사용 중인 닉네임입니다.");
            model.addAttribute("email", email);
            return "login/oauth2_nickname";
        }

        String encodedPassword = passwordEncoder.encode("OAUTH2");

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setNickname(nickname);
        newUser.setPassword(encodedPassword);
        newUser.setJoinDate(LocalDateTime.now());
        newUser.setMannerScore(BigDecimal.valueOf(36.5));
        newUser.setLocation("");

        userRepository.save(newUser);
        session.removeAttribute("oauth2_email");


        CustomUserDetails userDetails = new CustomUserDetails(newUser);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return "redirect:/"; // 메인으로 이동
    }
}