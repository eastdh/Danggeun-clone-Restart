package io.github.restart.gmo_danggeun.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/review")
public class ReviewController {

  @GetMapping
  public String reviewForm() {
    return "review/review_page";
  }
}
