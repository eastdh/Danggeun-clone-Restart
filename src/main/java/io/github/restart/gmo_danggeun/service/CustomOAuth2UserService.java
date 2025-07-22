package io.github.restart.gmo_danggeun.service;

import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.exception.OAuth2NicknameRequiredException;
import io.github.restart.gmo_danggeun.repository.UserRepository;
import io.github.restart.gmo_danggeun.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final ApplicationContext context;  // ApplicationContext로 대체

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User oAuth2User = super.loadUser(request);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        if (email == null) {
            throw new IllegalArgumentException("OAuth2 사용자 정보에서 이메일을 찾을 수 없습니다.");
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class); // 여기서 가져옴

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (!passwordEncoder.matches("OAUTH2", user.getPassword())) {
                throw new OAuth2AuthenticationException(
                        new OAuth2Error("email_conflict", "이미 일반 회원으로 가입된 이메일입니다.", null)
                );
            }

            return new CustomUserDetails(user, attributes);
        } else {
            OAuth2NicknameRequiredException cause = new OAuth2NicknameRequiredException(email);
            OAuth2Error error = new OAuth2Error("nickname_required", "닉네임이 필요한 사용자입니다.", null);
            throw new OAuth2AuthenticationException(error, cause);
        }
    }
}