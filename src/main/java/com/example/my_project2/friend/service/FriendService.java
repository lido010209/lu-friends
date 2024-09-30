package com.example.my_project2.friend.service;

import com.example.my_project2.friend.dto.FriendDto;
import com.example.my_project2.friend.entity.Friend;
import com.example.my_project2.friend.repo.FriendRepo;
import com.example.my_project2.service.WebService;
import com.example.my_project2.user.dto.UserDto;
import com.example.my_project2.user.entity.UserEntity;
import com.example.my_project2.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepo friendRepo;
    private final WebService webService;
    private final UserRepo userRepo;

    // Update-Unfriend
    public FriendDto unFriend(Long userId){
        UserEntity userLogin = webService.userLogin();
        UserEntity unfriend = webService.userById(userId);
        Friend friend;
        if (friendRepo.existsByUser1AndUser2(userLogin, unfriend))
            friend= friendRepo.findByUser1AndUser2(userLogin, unfriend).orElseThrow();
        else if (friendRepo.existsByUser1AndUser2(unfriend, userLogin))
            friend= friendRepo.findByUser1AndUser2(unfriend, userLogin).orElseThrow();
        else throw new IllegalArgumentException("No exist this friend");
        friend.setStatus(Friend.Status.Unfriend);
        return FriendDto.dto(friendRepo.save(friend));
    }

    // Delete - Block
    public void block(Long userId){
        UserEntity userLogin = webService.userLogin();
        UserEntity unfriend = webService.userById(userId);
        Friend friend;
        if (friendRepo.existsByUser1AndUser2(userLogin, unfriend)){
            friend= friendRepo.findByUser1AndUser2(userLogin, unfriend).orElseThrow();
            friendRepo.delete(friend);
        }
        else if (friendRepo.existsByUser1AndUser2(unfriend, userLogin)){
            friend= friendRepo.findByUser1AndUser2(unfriend, userLogin).orElseThrow();
            friendRepo.delete(friend);
        }
        else throw new IllegalArgumentException("No exist this friend");
    }

    //Read

    public List<FriendDto> allRequestOfSender(){
        UserEntity sender = webService.userLogin();
        List<FriendDto> dtos = new ArrayList<>();
        for (Friend suggest:
                friendRepo.findByStatusAndUser1(Friend.Status.Request, sender)) {
            dtos.add(FriendDto.dto(suggest));
        }
        return dtos;
    }

    public List<FriendDto> allRequestOfReceiver(){
        UserEntity receiver = webService.userLogin();
        List<FriendDto> dtos = new ArrayList<>();
        for (Friend suggest:
                friendRepo.findByStatusAndUser2(Friend.Status.Request, receiver)) {
            dtos.add(FriendDto.dto(suggest));
        }
        return dtos;
    }

    public List<UserDto> allFriend(){
        List<UserDto> friends = new ArrayList<>();
        UserEntity user = webService.userLogin();
        for (UserEntity userFriend: friendRepo.listFriends(user)){
            friends.add(UserDto.dto(userFriend));
        }
        return friends;
    }

}
