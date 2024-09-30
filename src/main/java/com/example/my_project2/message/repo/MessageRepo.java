package com.example.my_project2.message.repo;

import com.example.my_project2.message.entity.Message;
import com.example.my_project2.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message, Long> {
    @Query("SELECT DISTINCT m FROM Message m WHERE "+
            "(m.sender = :user1 AND m.receiver = :user2) OR "+
            "(m.sender = :user2 AND m.receiver = :user1) "+
            "ORDER BY m.createAt ASC")
    List<Message> chatMessages(@Param("user1") UserEntity user1,
                               @Param("user2") UserEntity user2);
    @Query("SELECT COUNT(DISTINCT m.sender) FROM Message m "+
            "WHERE m.receiver = :user AND m.confirm = false " +
            "GROUP BY m.sender")
    Integer countFriendMes(@Param("user") UserEntity user);

    List<Message> findBySenderAndReceiverAndConfirm(UserEntity sender, UserEntity receiver, boolean confirm);
}
