package com.roufit.backend.global.security.jwt;

import com.roufit.backend.domain.member.application.TokenService;
import com.roufit.backend.domain.member.dao.UserRepository;
import com.roufit.backend.domain.member.domain.User;
import com.roufit.backend.domain.member.dto.SecurityUserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("start jwt filter");

        final String accessToken = jwtService.extractAccessToken(request).orElse(null);
        final String refreshToken = jwtService.extractRefreshToken(request).orElse(null);

        if(accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info(accessToken);
        final String userEmail = jwtService.extractSubject(accessToken);
        User findUser = userRepository.findByEmail(userEmail)
                .orElseThrow(NoSuchElementException::new);

        if(refreshToken != null) {
            checkRefreshTokenAndRegeneratedToken(refreshToken, findUser, response);
        }

        if(refreshToken == null) {
            checkAccessToken(accessToken, findUser);
        }

        filterChain.doFilter(request, response);
    }

    private void checkRefreshTokenAndRegeneratedToken(String token, User user,
                                                     HttpServletResponse response) {
        log.info("Refresh 토큰 확인 및 검증");

        if(jwtService.isTokenValid(token)) {
            String accessToken = jwtService.generateToken(user.getEmail());
            String refreshToken = jwtService.generateRefreshToken(user.getEmail());

            jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
            tokenService.updateRefreshToken(user.getEmail(), refreshToken);
            saveAuthentication(user);
        }
    }

    private void checkAccessToken(String accessToken, User user) {
        log.info("Access 토큰 확인 및 검증");

        if(jwtService.isTokenValid(accessToken)) {
            log.info("유효한 토큰입니다.");
            saveAuthentication(user);
            return;
        }
        throw new IllegalArgumentException();
    }

    private void saveAuthentication(User user) {
        log.info("Authentication context에 저장");
        SecurityUserDto userDto = SecurityUserDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .role(user.getRole())
                .build();
        Authentication authentication = getAuthentication(userDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication getAuthentication(SecurityUserDto userDto) {
        return new UsernamePasswordAuthenticationToken(userDto,
                null,
                List.of(new SimpleGrantedAuthority(userDto.getRole().getKey())));
    }
}
