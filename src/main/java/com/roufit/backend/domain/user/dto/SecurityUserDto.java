package com.roufit.backend.domain.user.dto;

import com.roufit.backend.domain.user.domain.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
public class SecurityUserDto {

    private Long id;

    private String email;

    private String nickname;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public SecurityUserDto(Long id, String email,
                           String nickname, String profileImage,
                           Role role) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.role = role;
    }
}
