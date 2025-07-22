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
@IdClass(ImageTrade.class)
public class ImageTrade {

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trade_id")
  private Trade trade;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "image_id")
  private Image image;

  public ImageTrade() {
  }

  public ImageTrade(Trade trade, Image image) {
    this.trade = trade;
    this.image = image;
  }

  public Trade getTrade() {
    return trade;
  }

  public Image getImage() {
    return image;
  }

  public void setTrade(Trade trade) {
    this.trade = trade;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImageTrade imageTrade = (ImageTrade) o;
    return Objects.equals(trade, imageTrade.trade) && Objects.equals(image, imageTrade.image);
  }

  @Override
  public int hashCode() {
    return Objects.hash(trade, image);
  }
}