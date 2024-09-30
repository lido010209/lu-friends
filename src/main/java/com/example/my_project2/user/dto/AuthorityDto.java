package com.example.my_project2.user.dto;

import com.example.my_project2.user.entity.Authority;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class AuthorityDto {
    private Long id;
    private Authority.Role name;
    public static AuthorityDto dto(Authority authority){
        AuthorityDto authorityDto = AuthorityDto.builder()
                .id(authority.getId())
                .name(authority.getRole())
                .build();
        return authorityDto;
    }
}
