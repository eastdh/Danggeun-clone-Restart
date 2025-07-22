package io.github.restart.gmo_danggeun.repository.readonly;

import io.github.restart.gmo_danggeun.entity.readonly.TradeList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TradeListRepository extends ReadOnlyRepository<TradeList, Long> {
  @Query(value = """
      SELECT trade_id, title, price, status, hidden,
      created_at, updated_at, bump_updated_at,
      update_term, bump_update_term,
      user_id, location,
      category_id, category_name, img_url,
      ts_rank(search_vector, query) AS rank
      FROM trade_list,
      websearch_to_tsquery('simple', :keyword) AS query
      WHERE
      (:keyword IS NULL OR :keyword = '' OR search_vector @@ query) AND
      (:status IS NULL OR status = :status) AND
      (:location IS NULL OR location LIKE CONCAT('%', :location, '%')) AND
      (:category IS NULL OR category_name = :category) AND
      (:priceLowLimit IS NULL OR price >= :priceLowLimit) AND
      (:priceHighLimit IS NULL OR price <= :priceHighLimit)
      ORDER BY
      rank DESC NULLS LAST, bump_updated_at DESC NULLS LAST, updated_at DESC, created_at DESC
      """, nativeQuery = true)
  Page<TradeList> findAllByFilters(
      @Param("keyword") String keyword,
      @Param("location") String location,
      @Param("category") String category,
      @Param("priceLowLimit") Integer priceLowLimit,
      @Param("priceHighLimit") Integer priceHighLimit,
      @Param("status") String status,
      Pageable pageable);

  Page<TradeList> findAllByUserId(@Param("user_id") Long userId, Pageable pageable);
}
