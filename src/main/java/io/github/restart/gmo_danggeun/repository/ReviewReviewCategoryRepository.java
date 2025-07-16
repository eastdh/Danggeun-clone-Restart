package io.github.restart.gmo_danggeun.repository;

import io.github.restart.gmo_danggeun.entity.ReviewReviewCategory;
import io.github.restart.gmo_danggeun.entity.id.ReviewReviewCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewReviewCategoryRepository extends JpaRepository<ReviewReviewCategory, ReviewReviewCategoryId> {
    List<ReviewReviewCategory> findByReviewId(Long id);
}