package com.example.my_project2.friend.repo;

import com.example.my_project2.friend.entity.Friend;
import com.example.my_project2.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepo
        extends JpaRepository<Friend, Long> {
    List<Friend> findByStatusAndUser1(Friend.Status status, UserEntity user1);
    List<Friend> findByStatusAndUser2(Friend.Status status, UserEntity user2);
    boolean existsByUser1AndUser2(UserEntity user1, UserEntity user2);
    boolean existsByUser1AndUser2AndStatus(UserEntity user1, UserEntity user2,
                                         Friend.Status status);
    Optional<Friend> findByUser1AndUser2(UserEntity user1, UserEntity user2);
    Optional<Friend> findByUser1AndUser2AndStatus(UserEntity user1, UserEntity user2,
                                                  Friend.Status status);
    @Query("SELECT DISTINCT f.user2 FROM Friend f WHERE f.user1=:user AND f.status=Friend "+
            "UNION "+
            "SELECT DISTINCT f.user1 FROM Friend f WHERE f.user2=:user AND f.status=Friend")
    List<UserEntity> listFriends(@Param("user") UserEntity userLogin);
}
