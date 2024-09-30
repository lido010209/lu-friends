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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final FriendRepo friendRepo;
    private final WebService webService;

    // Create
    public FriendDto sendRequest(Long receiverId){
        UserEntity sender = webService.userLogin();
        UserEntity receiver = webService.userById(receiverId);
//        if (friendRepo.existsByUser1AndUser2AndStatus(sender, receiver, Friend.Status.Request))
//            throw new IllegalArgumentException("You already send request!!!");
//        else if (friendRepo.existsByUser1AndUser2AndStatus(sender, receiver, Friend.Status.Friend))
//            throw new IllegalArgumentException("You already send request!!!");
        if (friendRepo.existsByUser1AndUser2(receiver, sender) ||
        friendRepo.existsByUser1AndUser2(sender, receiver))
            throw new IllegalArgumentException("This request already exists!!!");
        Friend request = Friend.builder()
                .status(Friend.Status.Request)
                .user1(sender)
                .user2(receiver)
                .build();
        return FriendDto.dto(friendRepo.save(request));
    }

//    public List<FriendDto> listRequest(){
//        List<FriendDto> dtos = new ArrayList<>();
//        UserEntity sender = webService.userLogin();
//        for (Friend request: friendRepo.findByStatusAndUser1(Friend.Status.Request, sender)){
//            dtos.add(FriendDto.dto(request));
//        }
//        return dtos;
//    }

    public void cancelRequest(Long receiverId) {
        UserEntity sender = webService.userLogin();
        UserEntity receiver = webService.userById(receiverId);
        Friend request = friendRepo.findByUser1AndUser2(sender, receiver)
                .orElseThrow(() -> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "No exist any request friend!!!"));
        friendRepo.delete(request);
    }
}
