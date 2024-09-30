package com.example.my_project2.user.service;

import com.example.my_project2.user.entity.Authority;
import com.example.my_project2.user.repo.AuthorityRepo;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {
    private final AuthorityRepo authorityRepo;

    public AuthorityService(AuthorityRepo authorityRepo) {
        this.authorityRepo = authorityRepo;
        Authority authorityUser= Authority.builder()
                .role(Authority.Role.ROLE_USER).build();
        Authority authorityAdmin = Authority.builder()
                .role(Authority.Role.ROLE_ADMIN).build();
        Authority authorityViewer = Authority.builder()
                .role(Authority.Role.ROLE_VIEWER).build();
        Authority authorityOwner = Authority.builder()
                .role(Authority.Role.ROLE_OWNER).build();
        authorityRepo.save(authorityViewer);
        authorityRepo.save(authorityOwner);
        authorityRepo.save(authorityUser);
        authorityRepo.save(authorityAdmin);
    }

}
