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
public class NaverOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final AuthorityRepo authorityRepo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> responseMap = oAuth2User.getAttribute("response");
        String email = responseMap.get("email").toString();
        String phone= responseMap.get("mobile").toString();
        String username = responseMap.get("nickname").toString();
        String name = responseMap.get("name").toString();

        UserEntity user;
        if (userRepo.existsByEmail(email)){
            user = userRepo.findByEmail(email).orElseThrow();
        }
        else if(userRepo.existsByPhone(phone)){
            user = userRepo.findByPhone(phone).orElseThrow();
        }
        // If user doesn't exist in db -> create new
        else {
            user = UserEntity.builder()
                    .username(username)
                    .email(email)
                    .phone(phone)
                    .name(name)
                    .password(encoder.encode(responseMap.get("id").toString()))
                    .build();

            Authority authorityUser = authorityRepo.findByRole(Authority.Role.ROLE_USER).orElseThrow();
            Authority authorityViewer = authorityRepo.findByRole(Authority.Role.ROLE_VIEWER).orElseThrow();
            user.getAuthorities().add(authorityUser);
            user.getAuthorities().add(authorityViewer);
            user = userRepo.save(user);
        }

        UserEntity userEntity = userRepo.getUserWithAuthority(user.getId()).orElseThrow();
        UserDto dto = UserDto.dto(userEntity);
        Map<String, Object> attributes = dto.getAttributes();
        attributes.put("provider", "naver");

        DefaultOAuth2User newUser = new DefaultOAuth2User(dto.getAuthorities(),
                attributes, "username");
        return newUser;
    }
}
