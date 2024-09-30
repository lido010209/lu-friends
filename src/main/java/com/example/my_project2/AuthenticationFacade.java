package com.example.my_project2;

import com.example.my_project2.user.dto.UserDto;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public Authentication authentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
