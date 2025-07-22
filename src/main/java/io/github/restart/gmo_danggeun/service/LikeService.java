package io.github.restart.gmo_danggeun.service;

import io.github.restart.gmo_danggeun.repository.LikeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
  private final LikeRepository likeRepository;

  public LikeService(LikeRepository likeRepository) {
    this.likeRepository = likeRepository;
  }

  public List<Long> getLikedTradeIdList(Long id) {
    return likeRepository.findByUserId(id)
        .stream()
        .map(like -> like.getTrade().getId())
        .toList();
  }
}
