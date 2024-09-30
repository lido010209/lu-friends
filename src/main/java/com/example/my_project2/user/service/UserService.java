package com.example.my_project2.user.service;

import com.example.my_project2.user.dto.UserDto;
import com.example.my_project2.user.entity.Authority;
import com.example.my_project2.user.entity.UserEntity;
import com.example.my_project2.user.repo.AuthorityRepo;
import com.example.my_project2.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final AuthorityRepo authorityRepo;
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserEntity entity = userRepo.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("Username does not exists!!!")
        );
        UserEntity user = userRepo.getUserWithAuthority(entity.getId()).orElseThrow();
        return UserDto.dto(userRepo.save(user));
    }

    public UserDto create(UserDto dto){
        if (userRepo.existsByUsername(dto.getUsername())) throw new IllegalArgumentException("Username already exists!!!");
//        else if(userRepo.existsByEmail(dto.getEmail())) throw new IllegalArgumentException("Email already exists!!!");
//        else if(userRepo.existsByPhone(dto.getPhone())) throw new IllegalArgumentException("Phone already exists!!!");

        UserEntity user = UserEntity.builder()
                .username(dto.getUsername())
                .password(encoder.encode(dto.getPassword()))
                .profileImg("/static/visual/user.png")
                .build();
        Authority authority = authorityRepo.findByRole(Authority.Role.ROLE_VIEWER).orElseThrow();
        user.getAuthorities().add(authority);
        return UserDto.dto(userRepo.save(user));
    }


    public UserDto oneUser(Long userId){
        UserEntity user = userRepo.findById(userId).orElseThrow(
                ()-> new IllegalArgumentException("No exist user!!!")
        );
        return UserDto.dto(user);
    }
}
