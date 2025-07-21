package io.github.restart.gmo_danggeun.dto.trade;

public class LikeDto {

    private boolean isLiked;

    public LikeDto() {
    }

    public LikeDto(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}