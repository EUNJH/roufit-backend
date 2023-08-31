package com.roufit.backend.domain.member.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String deviceCode;

    private String email;

    private String nickname;

    private String profileImage;

    private Double weight;

    private Double height;

    private Double muscleMass;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;

    @Builder
    public User(String deviceCode, String email,
                String nickname, String profileImage,
                Double weight, Double height,
                Double muscleMass, Role role,
                SocialType socialType, String socialId) {
        this.deviceCode = deviceCode;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.weight = weight;
        this.height = height;
        this.muscleMass = muscleMass;
        this.role = role;
        this.socialType = socialType;
        this.socialId = socialId;
    }


}
