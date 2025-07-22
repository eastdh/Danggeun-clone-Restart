package io.github.restart.gmo_danggeun.dto.auth;

public class LoginResponseDto {

    private Long userId;
    private String nickname;
    private String email;

    public LoginResponseDto() {
    }

    public LoginResponseDto(Long userId, String nickname, String email) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}