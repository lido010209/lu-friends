package com.example.my_project2.friend;

import com.example.my_project2.friend.dto.FriendDto;
import com.example.my_project2.friend.service.RequestService;
import com.example.my_project2.friend.service.ResponseService;
import com.example.my_project2.friend.service.FriendService;
import com.example.my_project2.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("friends")
public class FriendController {
    private final RequestService requestService;
    private final FriendService friendService;
    private final ResponseService responseService;
    @PostMapping
    public FriendDto requestFriend(
            @RequestBody
            FriendDto dto
    ){
        return requestService.sendRequest(dto.getUser2Id());
    }

    @DeleteMapping("sender/{receiverId}")
    public void cancelRequest(
            @PathVariable("receiverId")
            Long receiverId
    ){
        requestService.cancelRequest(receiverId);
    }


    @PutMapping("receiver/accept")
    public FriendDto acceptFriend(
            @RequestBody
            FriendDto dto
    ){
        return responseService.acceptRequest(dto.getUser1Id());
    }

    @PutMapping("receiver/reject")
    public FriendDto rejectFriend(
            @RequestBody
            FriendDto dto
    ){
        return responseService.rejectRequest(dto.getUser1Id());
    }

    @GetMapping("sender")
    public List<FriendDto> allRequests(){
        return friendService.allRequestOfSender();
    }

    @GetMapping("receiver")
    public List<FriendDto> allRequestsOfReceiver(){
        return friendService.allRequestOfReceiver();
    }

    @GetMapping
    public List<UserDto> listFriends(){
        return friendService.allFriend();
    }
}
