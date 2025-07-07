package io.github.restart.gmo_danggeun.entity;

import io.github.restart.gmo_danggeun.entity.id.ImageTradeId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "image_trade")
@IdClass(ImageTradeId.class)
public class ImageTrade {

  @Id
  @ManyToOne
  @JoinColumn(name = "trade_id")
  private Trade trade;

  @Id
  @ManyToOne
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

  public void setTrade(Trade trade) {
    this.trade = trade;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImageTrade that = (ImageTrade) o;
    return Objects.equals(trade, that.trade) && Objects.equals(image, that.image);
  }

  @Override
  public int hashCode() {
    return Objects.hash(trade, image);
  }
}