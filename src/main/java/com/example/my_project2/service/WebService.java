package com.example.my_project2.service;

import com.example.my_project2.AuthenticationFacade;
import com.example.my_project2.message.repo.MessageRepo;
import com.example.my_project2.user.entity.UserEntity;
import com.example.my_project2.user.repo.UserRepo;
import com.example.my_project2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebService {
    private final UserRepo userRepo;
    private final MessageRepo messageRepo;

    public UserEntity userLogin(){
        String username = new AuthenticationFacade().authentication().getName();
        UserEntity entity = userRepo.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username does not exist"));
        UserEntity user = userRepo.getUserWithAuthority(entity.getId()).orElseThrow();
        return user;
    }

    public UserEntity userById(Long id){
        UserEntity entity = userRepo.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("Username does not exist"));
        UserEntity user = userRepo.getUserWithAuthority(entity.getId()).orElseThrow();
        return user;
    }

}
