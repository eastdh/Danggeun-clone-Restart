package io.github.restart.gmo_danggeun.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterDto {
    private String email;
    private String password;
    private String confirmPassword;
    private String nickname;
    private LocalDateTime joinDate = LocalDateTime.now();
}