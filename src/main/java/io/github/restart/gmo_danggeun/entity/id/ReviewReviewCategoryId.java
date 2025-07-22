package io.github.restart.gmo_danggeun.entity.id;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReviewReviewCategoryId implements Serializable {

    private Long reviewId;
    private Long reviewCategoryId;

    public ReviewReviewCategoryId() {}

    public ReviewReviewCategoryId(Long reviewId, Long reviewCategoryId) {
        this.reviewId = reviewId;
        this.reviewCategoryId = reviewCategoryId;
    }

    // equals, hashCode는 반드시 구현되어야 함
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewReviewCategoryId that)) return false;
        return Objects.equals(reviewId, that.reviewId) &&
                Objects.equals(reviewCategoryId, that.reviewCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, reviewCategoryId);
    }

    // getter & setter
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getReviewCategoryId() {
        return reviewCategoryId;
    }

    public void setReviewCategoryId(Long reviewCategoryId) {
        this.reviewCategoryId = reviewCategoryId;
    }
}