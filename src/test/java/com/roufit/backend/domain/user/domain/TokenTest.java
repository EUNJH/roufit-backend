package com.roufit.backend.domain.user.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenTest {

    @Test
    public void 토큰_유효성_테스트() throws Exception {
        //given
        Token revokedToken = Token.builder().revoked(true).expired(false).build();
        Token expiredToken = Token.builder().revoked(false).expired(true).build();
        Token revokedAndExpiredToken = Token.builder().revoked(true).expired(true).build();
        Token validToken = Token.builder().revoked(false).expired(false).build();
        //when & then
        assertThat(revokedToken.isValid()).isFalse();
        assertThat(expiredToken.isValid()).isFalse();
        assertThat(revokedAndExpiredToken.isValid()).isFalse();
        assertThat(validToken.isValid()).isTrue();
    }
}
