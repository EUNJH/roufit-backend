package com.roufit.backend.domain.user.application;

import com.roufit.backend.domain.user.dao.TokenRepository;
import com.roufit.backend.domain.user.dao.UserRepository;
import com.roufit.backend.domain.user.domain.Token;
import com.roufit.backend.domain.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
    @InjectMocks
    private TokenService tokenService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenRepository tokenRepository;

    @Test
    public void updateRefreshToken_기존토큰_없을때() throws Exception {
        //given
        String email = "test email";
        String token = "test token";
        given(userRepository.findByEmail(any())).willReturn(Optional.of(new User()));
        given(tokenRepository.findByUserEmail(any())).willReturn(null);
        given(tokenRepository.save(any())).willAnswer(t -> t.getArgument(0));
        //when
        Token newToken = tokenService.updateRefreshToken(email, token);
        //then
        assertThat(newToken.getRefreshToken()).isEqualTo(token);
        assertThat(newToken.isValid()).isTrue();
    }

    @Test
    public void updateRefreshToken_기존토큰_있을때() throws Exception {
        //given
        String email = "test email";
        String token = "test token";
        given(tokenRepository.findByUserEmail(any()))
                .willReturn(Token.builder()
                        .refreshToken("exist Token")
                        .revoked(true)
                        .expired(true)
                        .build());
        //when
        Token updatedToken = tokenService.updateRefreshToken(email, token);
        //then
        assertThat(updatedToken.getRefreshToken()).isEqualTo(token);
        assertThat(updatedToken.isValid()).isTrue();
    }
}