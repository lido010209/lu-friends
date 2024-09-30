package com.example.my_project2.user.repo;

import com.example.my_project2.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPhone(String phone);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    List<UserEntity> findByInterests(UserEntity.Interests interests);
    List<UserEntity> findByWorkplace(String workplace);
    List<UserEntity> findBySchool (String school);
    @Query("SELECT DISTINCT u FROM UserEntity u JOIN FETCH u.authorities WHERE u.id= :id")
    Optional<UserEntity> getUserWithAuthority(@Param("id") Long id);
//    @Query
//    List<UserEntity> sameInterests
}
