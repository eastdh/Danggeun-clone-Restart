package io.github.restart.gmo_danggeun.entity.readonly;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "trade_image_list")
@Immutable
public class TradeImageList {
  @Id
  @Column(name = "image_id")
  private Long imageId;

  @Column(name = "trade_id")
  private Long tradeId;

  @Column
  private String url;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "expire_date")
  private LocalDateTime expireDate;

  public TradeImageList() {
  }

  public TradeImageList(Long imageId, Long tradeId, String url, LocalDateTime createdAt,
      LocalDateTime expireDate) {
    this.imageId = imageId;
    this.tradeId = tradeId;
    this.url = url;
    this.createdAt = createdAt;
    this.expireDate = expireDate;
  }

  public Long getImageId() {
    return imageId;
  }

  public Long getTradeId() {
    return tradeId;
  }

  public String getUrl() {
    return url;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getExpireDate() {
    return expireDate;
  }
}
