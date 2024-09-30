package com.example.my_project2.token;

import com.example.my_project2.token.dto.RequestDto;
import com.example.my_project2.token.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("token")
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("issue")
    public ResponseDto getToken(@RequestBody RequestDto dto){
        return tokenService.getToken(dto);
    }

    @GetMapping("oauth")
    public ResponseDto validateTest(){
        return tokenService.tokenOAuth2();
    }
}
