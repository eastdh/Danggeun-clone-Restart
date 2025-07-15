package io.github.restart.gmo_danggeun.service;

import io.github.restart.gmo_danggeun.entity.Category;
import io.github.restart.gmo_danggeun.entity.readonly.TradeDetail;
import io.github.restart.gmo_danggeun.entity.readonly.TradeImageList;
import io.github.restart.gmo_danggeun.entity.readonly.TradeList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TradeService {
  Page<TradeList> searchTrades(String keyword, String location,
      String category, Integer priceLowLimit, Integer priceHighLimit,
      String status, Pageable pageable);

  Optional<TradeDetail> findById(Long id);

  List<TradeImageList> findAllImageById(Long id);

  Page<TradeList> findAllByUserId(Long userId, Pageable pageable);

  List<Category> findAllCategories();
}
