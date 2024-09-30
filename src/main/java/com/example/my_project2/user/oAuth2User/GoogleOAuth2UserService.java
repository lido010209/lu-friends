package com.example.my_project2.user.oAuth2User;

import com.example.my_project2.user.dto.UserDto;
import com.example.my_project2.user.entity.Authority;
import com.example.my_project2.user.entity.UserEntity;
import com.example.my_project2.user.repo.AuthorityRepo;
import com.example.my_project2.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepo userRepo;
    private final AuthorityRepo authorityRepo;
    private final PasswordEncoder encoder;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User= super.loadUser(userRequest);

//        Map<String, Object> googleAccount = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String username = oAuth2User.getName();
        String password = oAuth2User.getAttribute("sub");

        UserEntity user;
        if (userRepo.existsByEmail(email)){
            user= userRepo.findByEmail(email)
                    .orElseThrow();
        }
        else {
            user = UserEntity.builder()
                    .username(username)
                    .password(encoder.encode(password))
                    .name(name)
                    .email(email)
                    .phone(null)
                    .build();

            Authority authorityUser = authorityRepo.findByRole(Authority.Role.ROLE_USER).orElseThrow();
            Authority authorityViewer = authorityRepo.findByRole(Authority.Role.ROLE_VIEWER).orElseThrow();
            user.getAuthorities().add(authorityUser);
            user.getAuthorities().add(authorityViewer);
            userRepo.save(user);
        }
        UserDto dto = UserDto.dto(user);
        Map<String, Object> attributes = dto.getAttributes();
        attributes.put("provider", "google");
        return new DefaultOAuth2User(
                dto.getAuthorities(),
                attributes,
                "username"
        );
    }
}
