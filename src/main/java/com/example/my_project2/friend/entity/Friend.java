package com.example.my_project2.friend.entity;

import com.example.my_project2.baseEntity.BaseEntity;
import com.example.my_project2.message.entity.Message;
import com.example.my_project2.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Friend extends BaseEntity {
    @ManyToOne
    private UserEntity user1;
    @ManyToOne
    private UserEntity user2;
//    @OneToMany(mappedBy = "")
//    private final List<Message> messages = new ArrayList<>();
    @Enumerated(EnumType.STRING)
//    @Builder.Default
    private Status status;

    public enum Status {
        Request, Friend, Reject, Unfriend
    }
}
