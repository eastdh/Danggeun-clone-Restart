package io.github.restart.gmo_danggeun.entity;

import io.github.restart.gmo_danggeun.entity.id.ReviewReviewCategoryId;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "review_review_category")
public class ReviewReviewCategory {

    @EmbeddedId
    private ReviewReviewCategoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("reviewId")
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("reviewCategoryId")
    @JoinColumn(name = "review_category_id")
    private ReviewCategory reviewCategory;

    public ReviewReviewCategory() {}

    public ReviewReviewCategory(Review review, ReviewCategory reviewCategory, String reviewTarget) {
        this.review = review;
        this.reviewCategory = reviewCategory;
        this.id = new ReviewReviewCategoryId(review.getId(), reviewCategory.getId());
    }

    // Getter & Setter

    public ReviewReviewCategoryId getId() {
        return id;
    }

    public void setId(ReviewReviewCategoryId id) {
        this.id = id;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public ReviewCategory getReviewCategory() {
        return reviewCategory;
    }

    public void setReviewCategory(ReviewCategory reviewCategory) {
        this.reviewCategory = reviewCategory;
    }


    // equals & hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewReviewCategory that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}