package com.example.my_project2.user.entity;

import com.example.my_project2.baseEntity.BaseEntity;
import com.example.my_project2.friend.entity.Friend;
import com.example.my_project2.message.entity.Message;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "user")
public class UserEntity extends BaseEntity {
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String profileImg;

    private Integer friendSendMes;
//    private Integer mesCount;

    @ManyToMany(fetch = FetchType.LAZY)
    private final Set<Authority> authorities = new HashSet<>();

//    @OneToMany(mappedBy = "user1")
//    private final List<Friend> user1Friends = new ArrayList<>();
//    @OneToMany(mappedBy = "user2")
//    private final List<Friend> user2Friends = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Interests interests;

    private String school;
    private String workplace;
    private String country;
    private LocalDate birthday;

    // Location
    private Double latitude;
    private Double longitude;

    public enum Interests {
        GAME, MUSIC, PHOTOGRAPHY, ART, TRAVEL,
        DANCE, SPORTS, LANGUAGES, READING, BLOGGING;
    }
}
