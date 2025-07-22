package io.github.restart.gmo_danggeun.repository.readonly;

import io.github.restart.gmo_danggeun.entity.readonly.TradeDetail;
import java.util.Optional;

public interface TradeDetailRepository extends ReadOnlyRepository<TradeDetail, Long> {
  Optional<TradeDetail> findById(Long id);
}
