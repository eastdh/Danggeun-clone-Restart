package io.github.restart.gmo_danggeun.dto.chat;

public class ChatFilterRequestDto {
  private Long userId;             // 로그인한 사용자 ID
  private boolean showUnreadOnly;  // 읽지 않은 항목만 보기 여부

  public ChatFilterRequestDto() {
  }

  public ChatFilterRequestDto(Long userId, boolean showUnreadOnly) {
    this.userId = userId;
    this.showUnreadOnly = showUnreadOnly;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public boolean isShowUnreadOnly() {
    return showUnreadOnly;
  }

  public void setShowUnreadOnly(boolean showUnreadOnly) {
    this.showUnreadOnly = showUnreadOnly;
  }
}
