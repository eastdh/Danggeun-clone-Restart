package io.github.restart.gmo_danggeun.entity.id;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ImageTradeId implements Serializable {

  private Long tradeId;
  private Long imageId;

  public ImageTradeId() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ImageTradeId that = (ImageTradeId) o;
    return Objects.equals(tradeId, that.tradeId) &&
        Objects.equals(imageId, that.imageId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tradeId, imageId);
  }

  public Long getTradeId() {
    return tradeId;
  }

  public Long getImageId() {
    return imageId;
  }

  public void setTradeId(Long tradeId) {
    this.tradeId = tradeId;
  }

  public void setImageId(Long imageId) {
    this.imageId = imageId;
  }
}