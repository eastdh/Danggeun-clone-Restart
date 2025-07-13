package io.github.restart.gmo_danggeun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/main";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "login/register";
    }
    @GetMapping("/location")
    public String locationPage() {
        return "location/location";
    }
    @GetMapping("/profile")
    public String profilePage() {
        return "profile/profile";
    }
}