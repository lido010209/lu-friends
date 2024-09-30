package com.example.my_project2.friend.service;

import com.example.my_project2.friend.dto.FriendDto;
import com.example.my_project2.friend.entity.Friend;
import com.example.my_project2.friend.repo.FriendRepo;
import com.example.my_project2.service.WebService;
import com.example.my_project2.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final FriendRepo friendRepo;
    private final WebService webService;

    public FriendDto acceptRequest(Long senderId){
        UserEntity receiver = webService.userLogin();
        UserEntity sender = webService.userById(senderId);

        Friend request = friendRepo.findByUser1AndUser2(sender, receiver).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No exist any request friend!!!")
        );
        if (!request.getStatus().equals(Friend.Status.Request)){
            throw new IllegalArgumentException("No exist any request friend!!!");
        }
        request.setStatus(Friend.Status.Friend);
        return FriendDto.dto(friendRepo.save(request));
    }

    public FriendDto rejectRequest(Long senderId){
        UserEntity receiver = webService.userLogin();
        UserEntity sender = webService.userById(senderId);

        Friend request = friendRepo.findByUser1AndUser2(sender, receiver).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No exist any request friend!!!")
        );
        if (!request.getStatus().equals(Friend.Status.Request)){
            throw new IllegalArgumentException("No exist any request friend!!!");
        }
        request.setStatus(Friend.Status.Reject);
        return FriendDto.dto(friendRepo.save(request));
    }
}
