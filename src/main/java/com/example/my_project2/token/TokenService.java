package com.example.my_project2.token;

import com.example.my_project2.service.WebService;
import com.example.my_project2.token.dto.RequestDto;
import com.example.my_project2.token.dto.ResponseDto;
import com.example.my_project2.user.dto.UserDto;
import com.example.my_project2.user.entity.UserEntity;
import com.example.my_project2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final WebService webService;

    public ResponseDto getToken(RequestDto dto){
        UserDto user = (UserDto) userService.loadUserByUsername(dto.getUsername());
        if (!encoder.matches(dto.getPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Wrong password!!!");
        }

        ResponseDto responseDto = ResponseDto.builder()
                .token(tokenUtils.generateToken(user)).build();
        return responseDto;
    }

    public ResponseDto tokenOAuth2(){
        UserEntity user = webService.userLogin();
        UserDto userDto = UserDto.dto(user);
        String token = tokenUtils.generateToken(userDto);
        ResponseDto responseDto = ResponseDto.builder()
                .token(token).build();
        return responseDto;
    }
}
