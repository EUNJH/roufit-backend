package com.roufit.backend.domain.user.application;

import com.roufit.backend.domain.user.dao.TokenRepository;
import com.roufit.backend.domain.user.dao.UserRepository;
import com.roufit.backend.domain.user.domain.Token;
import com.roufit.backend.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public void updateRefreshToken(String email, String refreshToken) {
        Token token = tokenRepository.findByUserEmail(email);
        if(token == null) {
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
        token.updateToken(refreshToken);
    }
}
