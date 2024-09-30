package com.example.my_project2.user.entity;

import com.example.my_project2.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Authority extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role{
        ROLE_USER, ROLE_ADMIN, ROLE_OWNER, ROLE_VIEWER, READ, WRITE
    }

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private final Set<UserEntity> users = new HashSet<>();
}
