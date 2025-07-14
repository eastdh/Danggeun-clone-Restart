package io.github.restart.gmo_danggeun.service.impl;

import io.github.restart.gmo_danggeun.dto.FilterDto;
import io.github.restart.gmo_danggeun.entity.Category;
import io.github.restart.gmo_danggeun.entity.readonly.TradeDetail;
import io.github.restart.gmo_danggeun.entity.readonly.TradeImageList;
import io.github.restart.gmo_danggeun.entity.readonly.TradeList;
import io.github.restart.gmo_danggeun.repository.LikeRepository;
import io.github.restart.gmo_danggeun.repository.TradeRepository;
import io.github.restart.gmo_danggeun.repository.readonly.CategoryRepository;
import io.github.restart.gmo_danggeun.repository.readonly.TradeDetailRepository;
import io.github.restart.gmo_danggeun.repository.readonly.TradeImageListRepository;
import io.github.restart.gmo_danggeun.repository.readonly.TradeListRepository;
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
  private TradeListRepository tradeListRepository;
  private TradeDetailRepository tradeDetailRepository;
  private TradeImageListRepository tradeImageListRepository;
  private CategoryRepository categoryRepository;
  private LikeRepository likeRepository;

  public TradeServiceImpl(TradeRepository tradeRepository, TradeListRepository tradeListRepository,
      TradeDetailRepository tradeDetailRepository,
      TradeImageListRepository tradeImageListRepository,
      CategoryRepository categoryRepository, LikeRepository likeRepository) {
    this.tradeRepository = tradeRepository;
    this.tradeListRepository = tradeListRepository;
    this.tradeDetailRepository = tradeDetailRepository;
    this.tradeImageListRepository = tradeImageListRepository;
    this.categoryRepository = categoryRepository;
    this.likeRepository = likeRepository;
  }

  @Override
  public Page<TradeList> searchTrades(String keyword, String location, String category, int priceLowLimit, int priceHighLimit, Pageable pageable) {
    return tradeListRepository.findAllByFilters(keyword, location, category, priceLowLimit, priceHighLimit, pageable);
  }

  @Override
  public Optional<TradeDetail> findById(Long id) {
    return tradeDetailRepository.findById(id);
  }

  @Override
  public List<TradeImageList> findAllImageById(Long id) {
    return tradeImageListRepository.findAllByTradeId(id);
  }

  @Override
  public Page<TradeList> findAllByUserId(Long userId, Pageable pageable) {
    return tradeListRepository.findAllByUserId(userId, pageable);
  }

  @Override
  public List<Category> findAllCategories() {
    return categoryRepository.findAll();
  }
}
