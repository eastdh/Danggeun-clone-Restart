package io.github.restart.gmo_danggeun.entity;

import io.github.restart.gmo_danggeun.entity.id.ImageTradeId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "image_trade")
public class ImageTrade {

  @EmbeddedId
  private ImageTradeId id;

  @MapsId("tradeId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trade_id")
  private Trade trade;

  @MapsId("tradeId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "image_id")
  private Image image;

  public ImageTrade() {
  }

  public ImageTrade(ImageTradeId id) {
    this.id = id;
  }

  public ImageTradeId getId() {
    return id;
  }

  public void setId(ImageTradeId id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImageTrade that = (ImageTrade) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, id);
  }
}