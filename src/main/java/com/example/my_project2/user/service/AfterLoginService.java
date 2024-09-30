package com.example.my_project2.user.service;

import com.example.my_project2.AuthenticationFacade;
import com.example.my_project2.message.entity.Message;
import com.example.my_project2.message.repo.MessageRepo;
import com.example.my_project2.service.WebService;
import com.example.my_project2.user.dto.UserDto;
import com.example.my_project2.user.entity.Authority;
import com.example.my_project2.user.entity.UserEntity;
import com.example.my_project2.user.repo.AuthorityRepo;
import com.example.my_project2.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AfterLoginService {
    private final UserRepo userRepo;
    private final AuthorityRepo authorityRepo;
    private final PasswordEncoder encoder;
    private final WebService webService;
    private final MessageRepo messageRepo;

    public UserDto updateInfo(UserDto dto){
        UserEntity user = webService.userLogin();
        if (userRepo.existsByEmail(dto.getEmail()) && !user.getEmail().equals(dto.getEmail()))
            throw new IllegalArgumentException("Email already exists!!!");
        if (userRepo.existsByPhone(dto.getPhone()) && !user.getPhone().equals(dto.getPhone()))
            throw new IllegalArgumentException("Phone number already exists!!!");

        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setName(dto.getName());
        Authority authority = authorityRepo.findByRole(Authority.Role.ROLE_USER).orElseThrow();
        user.getAuthorities().add(authority);
        return UserDto.dto(userRepo.save(user));
    }

    public UserDto changePassword(String oldPassword, String newPassword){
        UserEntity user = webService.userLogin();

        if (!encoder.matches(oldPassword, user.getPassword()))
            throw new IllegalArgumentException("Password is wrong!!!");
        user.setPassword(encoder.encode(newPassword));
        return UserDto.dto(userRepo.save(user));
    }

    public UserDto updateImg(MultipartFile image){
        UserEntity user = webService.userLogin();

        String directory="profile/"+user.getId()+"/";
        try{
            Files.createDirectories(Path.of(directory));
        } catch (IOException exception){
            throw new RuntimeException("Fail to upload file");
        }
        String fileName= image.getOriginalFilename();
        String[] eleFile= fileName.split("\\.");
        String extension = eleFile[eleFile.length-1];

        String path = directory+ "profile."+extension;
        try{
            image.transferTo(Path.of(path));
        } catch (IOException exception){
            throw new RuntimeException("Fail to upload file");
        }

        String url = "/static/"+user.getId()+"/profile."+extension;
        user.setProfileImg(url);
        return UserDto.dto(userRepo.save(user));
    }

    public UserDto updateOtherInfo(UserDto dto){
        UserEntity user = webService.userLogin();
        user.setInterests(dto.getInterests());
        user.setBirthday(dto.getBirthday());
        user.setCountry(dto.getCountry());
        user.setWorkplace(dto.getWorkplace());
        return UserDto.dto(userRepo.save(user));
    }

    public UserDto readOne(){
        UserEntity user = webService.userLogin();
        Integer friendSendMes= messageRepo.countFriendMes(user);
        user.setFriendSendMes(friendSendMes);
        return UserDto.dto(userRepo.save(user));
    }
    public Integer mesCount(Long receiverId){
        UserEntity user = webService.userLogin();
        UserEntity friend = webService.userById(receiverId);
        List<Message> list = messageRepo.findBySenderAndReceiverAndConfirm(friend, user, false);
        Integer mesCount = list.size();
        return mesCount;
    }
}
