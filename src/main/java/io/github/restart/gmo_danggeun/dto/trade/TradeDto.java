package io.github.restart.gmo_danggeun.dto.trade;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class TradeDto {
  @NotNull(message = "카테고리는 필수입니다.")
  private Long categoryId;

  @NotBlank(message = "제목은 필수입니다.")
  @Size(min = 3, max = 20, message = "제목은 3자 이상 20자 이하여야 합니다.")
  private String title;

  @NotBlank(message = "설명은 필수입니다.")
  @Size(min = 10, max = 200, message = "설명은 10자 이상 200자 이하여야 합니다.")
  private String description;

  @Size(max = 50, message = "거래 희망 장소는 50자 이하여야 합니다.")
  private String preferredLocation;

  @NotNull(message = "가격은 필수입니다.")
  @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
  @Max(value = 999999999, message = "가격은 999,999,999원 이하여야 합니다.")
  private Integer price;

  @NotNull(message = "가격 제안 여부를 선택해주세요.")
  private Boolean offerable;

  @Pattern(regexp = "^(available|completed|reserved)$", message = "상태는 판매중, 거래오나료, 예약중만 가능합니다.")
  private String status;

  @Size(max = 5, message = "사진은 최대 5개까지만 첨부 가능합니다.")
  private List<MultipartFile> files;

  public TradeDto() {
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getPreferredLocation() {
    return preferredLocation;
  }

  public Integer getPrice() {
    return price;
  }

  public Boolean getOfferable() {
    return offerable;
  }

  public String getStatus() {
    return status;
  }

  public List<MultipartFile> getFiles() {
    return files;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setPreferredLocation(String preferredLocation) {
    this.preferredLocation = preferredLocation;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public void setOfferable(Boolean offerable) {
    this.offerable = offerable;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setFiles(List<MultipartFile> files) {
    this.files = files;
  }
}
