package io.github.restart.gmo_danggeun.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class OAuth2NicknameRequiredException extends AuthenticationException {

    private final String email;

    public OAuth2NicknameRequiredException(String email) {
        super("닉네임이 필요한 OAuth2 사용자입니다.");
        this.email = email;
    }

}