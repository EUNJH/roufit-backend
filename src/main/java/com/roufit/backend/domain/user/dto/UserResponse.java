package com.roufit.backend.domain.user.dto;

import com.roufit.backend.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String deviceCode;
    private String email;
    private String nickname;
    private String profileImage;
    private Double weight;
    private Double height;
    private Double muscleMass;
    private String role;
    private String socialType;

    @Builder
    public UserResponse(User user) {
        this.id = user.getId();
        this.deviceCode = user.getDeviceCode();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.weight = user.getWeight();
        this.height = user.getHeight();
        this.muscleMass = user.getMuscleMass();
        this.role = user.getRole().name();
        this.socialType = user.getSocialType().getSocialName();
    }

    public static UserResponse toDto(User user) {
        return UserResponse.builder()
                .user(user)
                .build();
    }

}
