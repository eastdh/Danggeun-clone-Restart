package io.github.restart.gmo_danggeun.service.impl;

import io.github.restart.gmo_danggeun.entity.Trade;
import io.github.restart.gmo_danggeun.repository.CategoryRepository;
import io.github.restart.gmo_danggeun.repository.LikeRepository;
import io.github.restart.gmo_danggeun.repository.TradeRepository;
import io.github.restart.gmo_danggeun.service.TradeService;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TradeServiceImpl implements TradeService {

  private TradeRepository tradeRepository;
  private LikeRepository likeRepository;
  private CategoryRepository categoryRepository;

  public TradeServiceImpl(TradeRepository tradeRepository, LikeRepository likeRepository,
      CategoryRepository categoryRepository) {
    this.tradeRepository = tradeRepository;
    this.likeRepository = likeRepository;
    this.categoryRepository = categoryRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Trade> findAllByLocation(String location, Pageable pageable) {
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Trade> searchTrades(String keyword, String location, String category,
      int priceLowLimit, int priceHighLimit, Pageable pageable) {
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Trade> findById(Long id) {
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Trade> findAllByUserId(Long userId) {
    return null;
  }
}
