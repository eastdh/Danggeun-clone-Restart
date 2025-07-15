package io.github.restart.gmo_danggeun.entity.readonly;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "trade_list")
@Immutable
public class TradeList {
  @Id
  @Column(name = "trade_id")
  private Long tradeId;

  @Column
  private String title;

  @Column
  private int price;

  @Column
  private String status;

  @Column
  private boolean hidden;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "bump_updated_at")
  private LocalDateTime bumpUpdatedAt;

  @Column(name = "update_term")
  private String updateTerm;

  @Column(name = "bump_update_term")
  private String bumpUpdateTerm;

  @Column(name = "user_id")
  private Long userId;

  @Column
  private String location;

  @Column(name = "category_id")
  private Long categoryId;

  @Column(name = "category_name")
  private String categoryName;

  @Column(name = "img_url")
  private String imgUrl;

  public TradeList() {
  }

  public TradeList(Long tradeId, String title, int price, String status, boolean hidden,
      LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime bumpUpdatedAt,
      String updateTerm, String bumpUpdateTerm, Long userId, String location, Long categoryId,
      String categoryName, String imgUrl) {
    this.tradeId = tradeId;
    this.title = title;
    this.price = price;
    this.status = status;
    this.hidden = hidden;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.bumpUpdatedAt = bumpUpdatedAt;
    this.updateTerm = updateTerm;
    this.bumpUpdateTerm = bumpUpdateTerm;
    this.userId = userId;
    this.location = location;
    this.categoryId = categoryId;
    this.categoryName = categoryName;
    this.imgUrl = imgUrl;
  }

  public Long getTradeId() {
    return tradeId;
  }

  public String getTitle() {
    return title;
  }

  public int getPrice() {
    return price;
  }

  public String getStatus() {
    return status;
  }

  public boolean isHidden() {
    return hidden;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public LocalDateTime getBumpUpdatedAt() {
    return bumpUpdatedAt;
  }

  public String getUpdateTerm() {
    return updateTerm;
  }

  public String getBumpUpdateTerm() {
    return bumpUpdateTerm;
  }

  public Long getUserId() {
    return userId;
  }

  public String getLocation() {
    return location;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public String getImgUrl() {
    return imgUrl;
  }
}
