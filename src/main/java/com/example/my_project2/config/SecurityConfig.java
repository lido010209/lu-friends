package com.example.my_project2.config;

import com.example.my_project2.token.TokenFilterHandler;
import com.example.my_project2.token.TokenOAuth2SuccessHandler;
import com.example.my_project2.token.TokenUtils;
import com.example.my_project2.user.oAuth2User.GoogleOAuth2UserService;
import com.example.my_project2.user.oAuth2User.KakaoOAuth2UserService;
import com.example.my_project2.user.oAuth2User.NaverOAuth2UserService;
import com.example.my_project2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final NaverOAuth2UserService naverService;
    private final KakaoOAuth2UserService kakaoService;
    private final GoogleOAuth2UserService googleService;
    private final TokenOAuth2SuccessHandler tokenOAuth2SuccessHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/static/**",
                            "/token/**", "/lu/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST,
                            "/users").permitAll();

                    auth.anyRequest().hasRole("VIEWER");

                })
                .addFilterBefore(
                        new TokenFilterHandler(
                                tokenUtils, userService
                        ),
                        AuthorizationFilter.class
                )
                .oauth2Login(oauth2->oauth2
                        .userInfoEndpoint(userInfo-> userInfo
                                .userService(oAuth2UserServiceSelector()))
                        .successHandler(tokenOAuth2SuccessHandler)
                        .permitAll())
        ;
        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserServiceSelector(){
        return userRequest -> {
            String registerId = userRequest.getClientRegistration().getRegistrationId();
            if ("naver".equals(registerId)){
                return naverService.loadUser(userRequest);
            }
            else if ("kakao".equals(registerId)){
                return kakaoService.loadUser(userRequest);
            }
            else if ("google".equals(registerId)){
                return googleService.loadUser(userRequest);
            }
            else {
                throw new OAuth2AuthenticationException("Unsupported provider"+ registerId);
            }
        };
    }
}
