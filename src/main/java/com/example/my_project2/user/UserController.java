package com.example.my_project2.user;

import com.example.my_project2.user.dto.UserDto;
import com.example.my_project2.user.service.AfterLoginService;
import com.example.my_project2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final AfterLoginService afterLoginService;
    @PostMapping
    public UserDto create(
            @RequestBody
            UserDto dto
    ){
        return userService.create(dto);
    }

    @GetMapping
    public UserDto readOne(){
        return afterLoginService.readOne();
    }

    @GetMapping("mes/{id}")
    public Integer mesCount(@PathVariable("id") Long friendId){
        return afterLoginService.mesCount(friendId);
    }

    @PutMapping
    public UserDto updateInfo(
            @RequestBody
            UserDto dto
    ){
        return afterLoginService.updateInfo(dto);
    }

    @PutMapping("profile")
    public UserDto updateOtherInfo(
            @RequestBody
            UserDto dto
    ){
        return afterLoginService.updateOtherInfo(dto);
    }
    @PutMapping("avatar")
    public UserDto updateImg(
            @RequestParam("avatar")
            MultipartFile avatar
    ){
        return afterLoginService.updateImg(avatar);
    }

    @GetMapping("{id}")
    public UserDto oneUser(@PathVariable("id") Long id){
        return userService.oneUser(id);
    }
}
