package io.github.restart.gmo_danggeun.dto.image;

import java.time.LocalDateTime;

public class ImageDto {

    private Long id;

    private Long uploaderId;

    private String url;

    private LocalDateTime createdAt;

    private LocalDateTime expireDate;

    public ImageDto() {
    }

    public ImageDto(Long id, Long uploaderId, String url, LocalDateTime createdAt,
        LocalDateTime expireDate) {
        this.id = id;
        this.uploaderId = uploaderId;
        this.url = url;
        this.createdAt = createdAt;
        this.expireDate = expireDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Long uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }
}