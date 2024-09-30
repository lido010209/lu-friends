package com.example.my_project2.token;

import com.example.my_project2.service.WebService;
import com.example.my_project2.user.dto.UserDto;
import com.example.my_project2.user.entity.UserEntity;
import com.example.my_project2.user.oAuth2User.GoogleOAuth2UserService;
import com.example.my_project2.user.oAuth2User.KakaoOAuth2UserService;
import com.example.my_project2.user.oAuth2User.NaverOAuth2UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenOAuth2SuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {
    private final WebService webService;
    private final TokenUtils tokenUtils;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        // This step will change to tokenService to get token
//        UserEntity user = webService.userLogin();
//
//        UserDto userDto = UserDto.dto(user);
//        String token = tokenUtils.generateToken(userDto);

        // return link to get token
        String targetUrl = String.format(
                "http://127.0.0.1:8080/lu/chat"
        );
        //redirect 한다
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
