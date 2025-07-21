package io.github.restart.gmo_danggeun.config;

import io.github.restart.gmo_danggeun.exception.OAuth2NicknameRequiredException;
import io.github.restart.gmo_danggeun.security.CustomAuthFailureHandler;
import io.github.restart.gmo_danggeun.security.OAuth2FailureHandler;
import io.github.restart.gmo_danggeun.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final CustomAuthFailureHandler customAuthFailureHandler;


    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService,
                          OAuth2FailureHandler oAuth2FailureHandler, CustomAuthFailureHandler customAuthFailureHandler) {
        this.userDetailsService = userDetailsService;
        this.oAuth2UserService = oAuth2UserService;
        this.oAuth2FailureHandler = oAuth2FailureHandler;
        this.customAuthFailureHandler = customAuthFailureHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/main", "/login", "/register", "/oauth2/nickname", "/trade", "/trade/{id}").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/assets/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureHandler(customAuthFailureHandler)
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)
                        )
                        .failureHandler(oAuth2FailureHandler)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }
}