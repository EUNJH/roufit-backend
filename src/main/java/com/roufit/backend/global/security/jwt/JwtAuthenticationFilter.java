package com.roufit.backend.global.security.jwt;

import com.roufit.backend.domain.user.application.TokenService;
import com.roufit.backend.domain.user.application.UserService;
import com.roufit.backend.domain.user.dao.UserRepository;
import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.global.error.exception.EntityNotFoundException;
import com.roufit.backend.global.error.exception.ErrorCode;
import com.roufit.backend.global.error.exception.InvalidRequestException;
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
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if(!request.getRequestURI().startsWith("/api/v1")) {
            log.info("인증이 필요하지 않는 API");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("start jwt filter");

        final String accessToken = jwtService.extractAccessToken(request).orElse(null);
        final String refreshToken = jwtService.extractRefreshToken(request).orElse(null);

        if(accessToken == null) {
            log.warn("엑세스 코드가 없는 요청");
            throw new InvalidRequestException(ErrorCode.NOT_ACCESS_TOKEN);
        }

        if(refreshToken != null) {
            checkRefreshTokenAndRegeneratedToken(refreshToken, response);
        }

        if(refreshToken == null) {
            checkAccessToken(accessToken);
        }

        String email = jwtService.extractSubject(accessToken);
        saveAuthentication(userService.findByEmail(email));

        filterChain.doFilter(request, response);
    }

    private void checkRefreshTokenAndRegeneratedToken(String refreshToken,
                                                     HttpServletResponse response) {
        log.info("Refresh 토큰 확인 및 검증");

        jwtService.isTokenValid(refreshToken);
        String email = jwtService.extractSubject(refreshToken);

        String newAccessToken = jwtService.generateToken(email);
        String newRefreshToken = jwtService.generateRefreshToken(email);

        jwtService.sendAccessAndRefreshToken(response, newAccessToken, newRefreshToken);
        tokenService.updateRefreshToken(email, newRefreshToken);
    }

    private void checkAccessToken(String accessToken) {
        log.info("Access 토큰 확인 및 검증");

        jwtService.isTokenValid(accessToken);
        log.info("유효한 토큰입니다.");
    }

    private void saveAuthentication(User user) {
        log.info("Authentication context에 저장");
        SecurityUserDto userDto = SecurityUserDto.builder()
                .id(user.getId())
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
                List.of(new SimpleGrantedAuthority(userDto.getRole().getAuthority())));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/login", "/login/**", "/swagger-ui/**",
                "**.html", "**.css", "**.js",
                "/swagger-resources/**", "/webjars/**", "/v3/api-docs/**"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }
}
