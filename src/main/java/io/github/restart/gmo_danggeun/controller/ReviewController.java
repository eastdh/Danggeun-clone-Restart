package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.dto.review.ReviewDto;
import io.github.restart.gmo_danggeun.entity.Review;
import io.github.restart.gmo_danggeun.entity.Trade;
import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.entity.ReviewCategory;
import io.github.restart.gmo_danggeun.repository.ReviewRepository;
import io.github.restart.gmo_danggeun.repository.TradeRepository;
import io.github.restart.gmo_danggeun.repository.UserRepository;
import io.github.restart.gmo_danggeun.repository.ReviewCategoryRepository;
import io.github.restart.gmo_danggeun.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

  private final TradeRepository tradeRepository;
  private final UserRepository userRepository;
  private final ReviewCategoryRepository reviewCategoryRepository;
  private final ReviewService reviewService;
  private final ReviewRepository reviewRepository;

  @GetMapping("/review/write")
  public String writeReviewPage(@RequestParam("trade_id") Long tradeId,
                                @RequestParam("partner_id") Long partnerId,
                                @RequestParam(value = "is_seller", required = false) Boolean isSeller,
                                @RequestParam("chat_room_id") Long chatRoomId,
                                Model model) {
    Trade trade = tradeRepository.findById(tradeId)
            .orElseThrow(() -> new IllegalArgumentException("거래를 찾을 수 없습니다."));
    User partner = userRepository.findById(partnerId)
            .orElseThrow(() -> new IllegalArgumentException("상대방을 찾을 수 없습니다."));

    String targetType = (isSeller != null && isSeller) ? "buyer" : "seller";
    List<ReviewCategory> categories = reviewCategoryRepository.findByReviewTarget(targetType);

    model.addAttribute("trade", trade);
    model.addAttribute("partnerId", partnerId);
    model.addAttribute("isSeller", isSeller);
    model.addAttribute("chatRoomId", chatRoomId);
    model.addAttribute("partnerNickname", partner.getNickname());
    model.addAttribute("partnerTemperature", partner.getMannerScore());
    model.addAttribute("categories", categories);

    return "review/review_page";
  }

  @PostMapping("/review/submit")
  public String submitReview(@RequestParam("trade_id") Long tradeId,
                             @RequestParam("partner_id") Long partnerId,
                             @RequestParam("rating") Short rating,
                             @RequestParam("content") String content,
                             @RequestParam(value = "categoryIds", required = false) List<Long> categoryIds,
                             @RequestParam("is_seller") boolean isSellerWriter,
                             @RequestParam("chat_room_id") Long chatRoomId,
                             Principal principal) {
    User writer = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new IllegalArgumentException("로그인된 유저를 찾을 수 없습니다."));
    reviewService.createReview(
            tradeId, writer.getId(), partnerId, content, rating, isSellerWriter, categoryIds);
    return "redirect:/chat?room_id=" + chatRoomId;
  }

  @GetMapping("/review/review_view")
  public String viewReview(@RequestParam("trade_id") Long tradeId,
                           @RequestParam("partner_id") Long partnerId,
                           Principal principal,
                           Model model) {
    User currentUser = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new IllegalArgumentException("로그인된 유저를 찾을 수 없습니다."));

    Review review = reviewRepository.findByTradeIdAndUserId(tradeId, currentUser.getId())
            .orElseThrow(() -> new IllegalArgumentException("작성된 리뷰를 찾을 수 없습니다."));

    ReviewDto reviewDto = reviewService.convertToDto(review);

    User partner = userRepository.findById(partnerId)
            .orElseThrow(() -> new IllegalArgumentException("상대방 유저를 찾을 수 없습니다."));

    model.addAttribute("review", reviewDto);
    model.addAttribute("partner", partner);
    model.addAttribute("tradeId", tradeId);

    return "review/review_view";
  }
}
