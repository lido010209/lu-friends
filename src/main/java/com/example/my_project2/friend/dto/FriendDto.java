package com.example.my_project2.friend.dto;

import com.example.my_project2.friend.entity.Friend;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class FriendDto {
    private Long id;
    private Friend.Status status;
    private Long user1Id;
    private Long user2Id;
    public static FriendDto dto(Friend suggest){
        FriendDto friendDto = FriendDto.builder()
                .status(suggest.getStatus())
                .id(suggest.getId())
                .user1Id(suggest.getUser1().getId())
                .user2Id(suggest.getUser2().getId())
                .build();
        return friendDto;
    }
}
