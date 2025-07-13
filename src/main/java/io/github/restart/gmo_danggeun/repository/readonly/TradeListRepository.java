package io.github.restart.gmo_danggeun.repository.readonly;

import io.github.restart.gmo_danggeun.dto.FilterDto;
import io.github.restart.gmo_danggeun.entity.readonly.TradeList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TradeListRepository extends ReadOnlyRepository<TradeList, Long> {
  @Query(value = """
      SELECT trade_id, title, price, status, category_name, hidden, updated_at, location, img_url
      FROM trade_list
      WHERE
      (:#{#filter.keyword} IS NULL OR LOWER(title) LIKE LOWER(CONCAT('%', :#{#filter.keyword}, '%'))
      OR LOWER(description) LIKE LOWER(CONCAT('%', :#{#filter.keyword}, '%'))) AND
      (:#{#filter.location} IS NULL OR location LIKE CONCAT('%', :#{#filter.location}, '%')) AND
      (:#{#filter.category} IS NULL OR category_name = ':#{#filter.category}') AND
      (:#{#filter.priceLowLimit} IS NULL OR price >= :#{#filter.priceLowLimit}) AND
      (:#{#filter.priceHighLimit} IS NULL OR price <= :#{#filter.priceHighLimit})
      ORDER BY created_at, updated_at
      """, nativeQuery = true)
  Page<TradeList> findAllByFilters(@Param("filter") FilterDto filter, Pageable pageable);

  Page<TradeList> findAllByUserId(@Param("user_id") Long userId, Pageable pageable);
}
