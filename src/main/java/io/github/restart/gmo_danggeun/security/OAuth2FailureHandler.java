package io.github.restart.gmo_danggeun.security;

import io.github.restart.gmo_danggeun.exception.OAuth2NicknameRequiredException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        if (exception.getCause() instanceof OAuth2NicknameRequiredException oauth2Ex) {
            request.getSession().setAttribute("oauth2_email", oauth2Ex.getEmail());
            response.sendRedirect("/oauth2/nickname");
        } else {
            response.sendRedirect("/login?error=true");
        }
    }
}