package io.github.restart.gmo_danggeun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping("/main")
  public String mainPage() {
    return "main/main_page";
  }

}
