package io.github.restart.gmo_danggeun.repository;

import io.github.restart.gmo_danggeun.entity.ReviewReviewCategory;
import io.github.restart.gmo_danggeun.entity.id.ReviewReviewCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewReviewCategoryRepository extends JpaRepository<ReviewReviewCategory, ReviewReviewCategoryId> {

    // 리뷰별 카테고리 목록 조회
    List<ReviewReviewCategory> findByReviewId(Long reviewId);

    // 특정 카테고리에 속한 모든 리뷰 연결 조회
    List<ReviewReviewCategory> findByReviewCategoryId(Long categoryId);

    // 리뷰 삭제 시 연결 관계 삭제
    void deleteByReviewId(Long reviewId);
}