package com.example.my_project2.user.dto;

import com.example.my_project2.user.entity.Authority;
import com.example.my_project2.user.entity.UserEntity;
import com.example.my_project2.user.repo.UserRepo;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDate;
import java.util.*;


@AllArgsConstructor
@Builder
//@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
public class UserDto implements UserDetails, OAuth2User {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String profileImg;
    private final Set<String> stringAuthorities = new HashSet<>();

    private UserEntity.Interests interests;
    private String workplace;
    private String country;
    private String school;
    private LocalDate birthday;
    private Integer friendSendMes;
//    private Integer mesCount;


    public static UserDto dto (UserEntity entity){
        UserDto userDto = UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .name(entity.getName())
                .country(entity.getCountry())
                .workplace(entity.getWorkplace())
                .interests(entity.getInterests())
                .birthday(entity.getBirthday())
                .school(entity.getSchool())
                .profileImg(entity.getProfileImg())
                .friendSendMes(entity.getFriendSendMes())
//                .mesCount(entity.getMesCount())
                .build();
        for (Authority authority: entity.getAuthorities()){
            userDto.getStringAuthorities().add(authority.getRole().toString());
        }
        return userDto;
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("username", username);
        attributes.put("password", password);
        attributes.put("name", name);
        attributes.put("email", email);
        attributes.put("phone", phone);
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return stringAuthorities.stream().map(SimpleGrantedAuthority::new).toList();
    }
}
