package com.example.my_project2.user.repo;

import com.example.my_project2.user.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepo extends JpaRepository<Authority, Long> {
    boolean existsByRole(Authority.Role role);
    Optional<Authority> findByRole(Authority.Role role);
}
