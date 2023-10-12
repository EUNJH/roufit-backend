package com.roufit.backend.global.security.oauth2.handler;

import com.roufit.backend.domain.user.application.TokenService;
import com.roufit.backend.global.security.jwt.JwtService;
import com.roufit.backend.global.security.oauth2.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthorizationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication
    ) throws IOException, ServletException {
        log.info("로그인 성공");
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(oAuth2User.getEmail());
        String refreshToken = jwtService.generateRefreshToken(oAuth2User.getEmail());

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        tokenService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
    }

}
