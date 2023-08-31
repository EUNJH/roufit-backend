package com.roufit.backend.domain.member.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Token {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String refreshToken;

    private boolean revoked;

    private boolean expired;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public boolean isValid() {
        return !(revoked || expired);
    }

    public void updateToken(String token) {
        this.refreshToken = token;
        this.revoked = false;
        this.expired = false;
    }

    @Builder
    public Token(Long id, String refreshToken, boolean revoked, boolean expired, User user) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.revoked = revoked;
        this.expired = expired;
        this.user = user;
    }
}
