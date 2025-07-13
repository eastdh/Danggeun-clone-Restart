package io.github.restart.gmo_danggeun.service;

import io.github.restart.gmo_danggeun.dto.FilterDto;
import io.github.restart.gmo_danggeun.entity.readonly.TradeDetail;
import io.github.restart.gmo_danggeun.entity.readonly.TradeList;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TradeService {
  Page<TradeList> searchTrades(FilterDto filter, Pageable pageable);

  Optional<TradeDetail> findById(Long id);

  // Todo : add image get method(ImageTrade, Image)
  //List<ImageTrade> findAllImageById(Long id);

  Page<TradeList> findAllByUserId(Long userId, Pageable pageable);
}
