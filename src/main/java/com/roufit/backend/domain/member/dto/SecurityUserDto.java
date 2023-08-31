package com.roufit.backend.domain.member.dto;

import com.roufit.backend.domain.member.domain.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
public class SecurityUserDto {

    private String email;

    private String nickname;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public SecurityUserDto(String email, String nickname, String profileImage, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.role = role;
    }
}
