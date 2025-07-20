package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.dto.auth.RegisterDto;
import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // BCrypt 주입

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("register", new RegisterDto());
        return "login/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("register") RegisterDto dto, Model model) {
        // 이메일 중복 검사
        if (userRepository.existsByEmail(dto.getEmail())) {
            model.addAttribute("error", "이미 사용 중인 이메일입니다.");
            return "login/register";
        }

        // 이메일 형식 검사
        if (!dto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            model.addAttribute("error", "유효한 이메일 형식이 아닙니다.");
            return "login/register";
        }

        // 닉네임 중복 검사
        if (userRepository.existsByNickname(dto.getNickname())) {
            model.addAttribute("error", "이미 사용 중인 닉네임입니다.");
            return "login/register";
        }

        // 닉네임 길이/형식 검사
        if (!dto.getNickname().matches("^[가-힣a-zA-Z0-9]{2,8}$")) {
            model.addAttribute("error", "닉네임은 2~8자의 한글, 영문, 숫자만 사용 가능합니다.");
            return "login/register";
        }

        // 비밀번호 길이 및 문자 조건 검사
        if (!dto.getPassword().matches("^[A-Za-z0-9!@#$%^&*()_+\\-=]{8,20}$")) {
            model.addAttribute("error", "비밀번호는 8~20자이며, 영어, 숫자, 특수문자만 사용할 수 있습니다.");
            return "login/register";
        }

        // 비밀번호 확인
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "login/register";
        }

        // *** 비밀번호 해싱(BCrypt 적용) ***
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        // 회원 저장
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(encodedPassword);  // 해싱된 비밀번호로 저장
        user.setNickname(dto.getNickname());
        user.setJoinDate(dto.getJoinDate());

        userRepository.save(user);

        String successMessage = URLEncoder.encode("회원가입이 완료되었습니다. 다시 로그인해주세요.", StandardCharsets.UTF_8);
        return "redirect:/login?success=" + successMessage;
    }
}