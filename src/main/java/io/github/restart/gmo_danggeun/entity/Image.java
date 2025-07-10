package io.github.restart.gmo_danggeun.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "image")
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "uploader_id", nullable = false)
  private User uploader;

  @Column(length = 100, nullable = false)
  private String url;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "expire_date", nullable = false)
  private LocalDateTime expireDate;

  public Image() {
  }

  public Image(Long id, User uploader, String url, LocalDateTime createdAt, LocalDateTime expireDate) {
    this.id = id;
    this.uploader = uploader;
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

  public User getUploader() {
    return uploader;
  }

  public void setUploader(User uploader) {
    this.uploader = uploader;
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

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Image image = (Image) o;
    return Objects.equals(id, image.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}