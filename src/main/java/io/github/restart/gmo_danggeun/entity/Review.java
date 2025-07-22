package io.github.restart.gmo_danggeun.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "review")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "trade_id", nullable = false)
  private Trade trade;

  @ManyToOne
  @JoinColumn(name = "buyer_id", nullable = false)
  private User buyer;

  @ManyToOne
  @JoinColumn(name = "seller_id", nullable = false)
  private User seller;

  @Column(length = 200)
  private String content;

  @Column(nullable = false)
  private Short rating;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "is_seller_writer")
  private Boolean isSellerWriter;

  public Review() {}

  public Review(Long id, Trade trade, User buyer, User seller, String content,
                Short rating, LocalDateTime createdAt, LocalDateTime updatedAt,
                Boolean isSellerWriter) {
    this.id = id;
    this.trade = trade;
    this.buyer = buyer;
    this.seller = seller;
    this.content = content;
    this.rating = rating;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.isSellerWriter = isSellerWriter;
  }

  // Getter/Setter
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Trade getTrade() { return trade; }
  public void setTrade(Trade trade) { this.trade = trade; }
  public User getBuyer() { return buyer; }
  public void setBuyer(User buyer) { this.buyer = buyer; }
  public User getSeller() { return seller; }
  public void setSeller(User seller) { this.seller = seller; }
  public String getContent() { return content; }
  public void setContent(String content) { this.content = content; }
  public Short getRating() { return rating; }
  public void setRating(Short rating) { this.rating = rating; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
  public LocalDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
  public Boolean getSellerWriter() { return isSellerWriter; }
  public void setSellerWriter(Boolean sellerWriter) { isSellerWriter = sellerWriter; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Review)) return false;
    Review review = (Review) o;
    return Objects.equals(id, review.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
