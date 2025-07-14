package io.github.restart.gmo_danggeun.entity.readonly;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
public class TradeDetail {
  @Id
  @Column(name = "trade_id")
  private Long tradeId;

  @Column(name = "category_id")
  private Long categoryId;
  @Column(name = "category_name")
  private String categoryName;

  @Column
  private String title;

  @Column
  private String description;

  @Column(name = "preferred_location")
  private String preferredLocation;

  @Column
  private Integer price;

  @Column(name = "is_offerable")
  private Boolean isOfferable;

  @Column
  private String status;

  @Column
  private Boolean hidden;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "like_counts")
  private int likeCounts;

  @Column(name = "chat_counts")
  private int chatCounts;

  @Column(name = "user_id")
  private Long userId;

  @Column
  private String nickname;

  @Column
  private String location;

  @Column(name = "manner_score")
  private double mannerScore;

  public TradeDetail() {
  }

  public TradeDetail(Long tradeId, Long categoryId, String categoryName, String title,
      String description, String preferredLocation, Integer price, Boolean isOfferable,
      String status,
      Boolean hidden, LocalDateTime createdAt, LocalDateTime updatedAt, int likeCounts,
      int chatCounts, Long userId, String nickname, String location, double mannerScore) {
    this.tradeId = tradeId;
    this.categoryId = categoryId;
    this.categoryName = categoryName;
    this.title = title;
    this.description = description;
    this.preferredLocation = preferredLocation;
    this.price = price;
    this.isOfferable = isOfferable;
    this.status = status;
    this.hidden = hidden;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.likeCounts = likeCounts;
    this.chatCounts = chatCounts;
    this.userId = userId;
    this.nickname = nickname;
    this.location = location;
    this.mannerScore = mannerScore;
  }

  public Long getTradeId() {
    return tradeId;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getPreferredLocation() {
    return preferredLocation;
  }

  public Integer getPrice() {
    return price;
  }

  public Boolean getOfferable() {
    return isOfferable;
  }

  public String getStatus() {
    return status;
  }

  public Boolean getHidden() {
    return hidden;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public int getLikeCounts() {
    return likeCounts;
  }

  public int getChatCounts() {
    return chatCounts;
  }

  public Long getUserId() {
    return userId;
  }

  public String getNickname() {
    return nickname;
  }

  public String getLocation() {
    return location;
  }

  public double getMannerScore() {
    return mannerScore;
  }
}
