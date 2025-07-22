package io.github.restart.gmo_danggeun.repository;

import io.github.restart.gmo_danggeun.entity.ReviewCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewCategoryRepository extends JpaRepository<ReviewCategory, Long> {

    // 특정 이름으로 카테고리 찾기 (중복 방지용)
    Optional<ReviewCategory> findByName(String name);

    // 특정 리뷰 타겟(buyer/seller)에 해당하는 카테고리들만 가져오기
    List<ReviewCategory> findByReviewTarget(String reviewTarget);
}