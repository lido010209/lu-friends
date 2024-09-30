package com.example.my_project2.message.entity;

import com.example.my_project2.baseEntity.BaseEntity;
import com.example.my_project2.user.entity.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message extends BaseEntity {
    private String content;
    private boolean confirm;
    @ManyToOne
    private UserEntity sender;
    @ManyToOne
    private UserEntity receiver;
    private Long replyId;
    private String reply;
}
