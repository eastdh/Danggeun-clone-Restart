package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.dto.auth.RegisterDto;
import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserRepository userRepository;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("register", new RegisterDto());
        return "login/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("register") RegisterDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setNickname(dto.getNickname());
        user.setJoinDate(dto.getJoinDate());

        userRepository.save(user);
        return "redirect:/login";
    }
}