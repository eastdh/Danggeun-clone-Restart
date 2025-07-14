package io.github.restart.gmo_danggeun.dto.trade;

public class FilterDTO {
  private String location;
  private String category;
  private int priceLowLimit;
  private int priceHighLimit;

  public FilterDTO() {
  }

  public FilterDTO(String location, String category, int priceLowLimit, int priceHighLimit) {
    this.location = location;
    this.category = category;
    this.priceLowLimit = priceLowLimit;
    this.priceHighLimit = priceHighLimit;
  }

  public String getLocation() {
    return location;
  }

  public String getCategory() {
    return category;
  }

  public int getPriceLowLimit() {
    return priceLowLimit;
  }

  public int getPriceHighLimit() {
    return priceHighLimit;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public void setPriceLowLimit(int priceLowLimit) {
    this.priceLowLimit = priceLowLimit;
  }

  public void setPriceHighLimit(int priceHighLimit) {
    this.priceHighLimit = priceHighLimit;
  }
}
