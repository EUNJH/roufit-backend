package com.roufit.backend.domain.user.application;

import com.roufit.backend.domain.user.dao.TokenRepository;
import com.roufit.backend.domain.user.dao.UserRepository;
import com.roufit.backend.domain.user.domain.Token;
import com.roufit.backend.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public void updateRefreshToken(String email, String refreshToken) {
        Token token = tokenRepository.findByUserEmail(email);
        if(token == null) {
            log.info("새로운 토큰 저장");
            User findUser = userRepository.findByEmail(email)
                    .orElseThrow(NoSuchElementException::new);
            tokenRepository.save(Token.builder()
                            .refreshToken(refreshToken)
                            .expired(false)
                            .revoked(false)
                            .user(findUser)
                            .build()
            );
            return;
        }
        log.info("기존 토큰 업데이트");
        token.updateToken(refreshToken);
    }
}
