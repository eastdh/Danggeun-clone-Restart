package io.github.restart.gmo_danggeun.dto;

public class FilterDto {
  private String category;
  private Integer priceLowLimit;
  private Integer priceHighLimit;

  private String status;

  public FilterDto() {
  }

  public FilterDto(String category, Integer priceLowLimit, Integer priceHighLimit, String status) {
    this.category = category;
    this.priceLowLimit = priceLowLimit;
    this.priceHighLimit = priceHighLimit;
    this.status = status;
  }

  public String getCategory() {
    return category;
  }

  public Integer getPriceLowLimit() {
    return priceLowLimit;
  }

  public Integer getPriceHighLimit() {
    return priceHighLimit;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public void setPriceLowLimit(Integer priceLowLimit) {
    this.priceLowLimit = priceLowLimit;
  }

  public void setPriceHighLimit(Integer priceHighLimit) {
    this.priceHighLimit = priceHighLimit;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
