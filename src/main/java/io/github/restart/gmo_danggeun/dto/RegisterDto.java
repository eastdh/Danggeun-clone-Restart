package io.github.restart.gmo_danggeun.dto;

import java.time.LocalDateTime;

public class RegisterDto {

    private String email;
    private String password;
    private String nickname;
    private String location;
    private LocalDateTime joinDate;

    public RegisterDto() {
    }

    public RegisterDto(String email, String password, String nickname, String location, LocalDateTime joinDate) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.location = location;
        this.joinDate = joinDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }
}