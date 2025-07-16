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
@Table(name = "trade")
public class Trade {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @Column(length = 20, nullable = false)
  private String title;

  @Column(length = 200, nullable = false)
  private String description;

  @Column(name = "preferred_location", length = 50)
  private String preferredLocation;

  @Column
  private Integer price;

  @Column(name = "is_offerable", nullable = false)
  private Boolean isOfferable;


  @Column(name = "bump_count", nullable = false)
  private Integer bumpCount;

  @Column(length = 10)
  private String status;

  @Column
  private Boolean hidden;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  public Trade() {
  }

  public Trade(Long id, User user, Category category, String title, String description, String preferredLocation,
               Integer price, Boolean isOfferable, Integer bumpCount, String status, Boolean hidden,
               LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.user = user;
    this.category = category;
    this.title = title;
    this.description = description;
    this.preferredLocation = preferredLocation;
    this.price = price;
    this.isOfferable = isOfferable;
    this.bumpCount = bumpCount;
    this.status = status;
    this.hidden = hidden;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Trade(User user, Category category, String title, String description,
               String preferredLocation, Integer price, Boolean isOfferable, String status, Boolean hidden) {
    this.user = user;
    this.category = category;
    this.title = title;
    this.description = description;
    this.preferredLocation = preferredLocation;
    this.price = price;
    this.isOfferable = isOfferable;
    this.status = status;
    this.hidden = hidden;
    this.updatedAt = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPreferredLocation() {
    return preferredLocation;
  }

  public void setPreferredLocation(String preferredLocation) {
    this.preferredLocation = preferredLocation;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Boolean getOfferable() {
    return isOfferable;
  }

  public void setOfferable(Boolean offerable) {
    isOfferable = offerable;
  }

  public Integer getBumpCount() {
    return bumpCount;
  }

  public void setBumpCount(Integer bumpCount) {
    this.bumpCount = bumpCount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Boolean getHidden() {
    return hidden;
  }

  public void setHidden(Boolean hidden) {
    this.hidden = hidden;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Trade trade = (Trade) o;
    return Objects.equals(id, trade.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
