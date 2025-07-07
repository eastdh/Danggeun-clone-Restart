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

  public Review() {
  }

  public Review(Long id, Trade trade, User buyer, User seller, String content, Short rating, LocalDateTime createdAt,
      LocalDateTime updatedAt, Boolean isSellerWriter) {
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

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Review review = (Review) o;
    return Objects.equals(id, review.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
