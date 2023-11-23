package com.roufit.backend.global.security.jwt;

import com.roufit.backend.domain.user.application.TokenService;
import com.roufit.backend.domain.user.application.UserService;
import com.roufit.backend.domain.user.domain.Role;
import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.global.error.exception.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {
    @InjectMocks
    private JwtAuthenticationFilter filter;
    @Mock
    private TokenService tokenService;
    @Mock
    private UserService userService;
    @Mock
    private JwtService jwtService;
    MockFilterChain filterChain;
    MockHttpServletRequest request;
    MockHttpServletResponse response;

    @BeforeEach
    public void init() {
        this.filterChain = new MockFilterChain();
        this.request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1");
        this.response = new MockHttpServletResponse();
    }

    @Test
    public void 필터를_거치지않을_api() throws Exception {
        //given
        request.setRequestURI("/abc/v2");
        //when & then
        filter.doFilterInternal(request, response, filterChain);
    }

    @Test
    public void accessToken_없을때() throws Exception {
        //when & then
        assertThrows(InvalidRequestException.class,
                () -> filter.doFilterInternal(request, response, filterChain));
    }

    @Test
    public void invalid_accessToken() throws Exception {
        //given
        given(jwtService.extractAccessToken(any())).willReturn(Optional.of("Test Access"));
        given(jwtService.extractRefreshToken(any())).willReturn(Optional.empty());
        given(jwtService.isTokenValid(any()))
                .willThrow(InvalidRequestException.class);

        //when & then
        assertThrows(InvalidRequestException.class,
                () -> filter.doFilterInternal(request, response, filterChain));
    }

    @Test
    public void valid_accessToken() throws Exception {
        //given
        given(jwtService.extractAccessToken(any())).willReturn(Optional.of("Test Access"));
        given(jwtService.extractRefreshToken(any())).willReturn(Optional.empty());
        given(jwtService.isTokenValid(any()))
                .willReturn(true);
        User testUser = User.builder()
                .email("test user")
                .nickname("test user")
                .profileImage("test image")
                .role(Role.USER)
                .build();
        given(userService.findByEmail(any()))
                .willReturn(testUser);

        //when
        filter.doFilterInternal(request, response, filterChain);
        //then
        SecurityUserDto userDto = (SecurityUserDto) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        assertThat(testUser.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(testUser.getNickname()).isEqualTo(userDto.getNickname());
        assertThat(testUser.getRole()).isEqualTo(userDto.getRole());
        assertThat(testUser.getProfileImage()).isEqualTo(userDto.getProfileImage());
    }

    @Test
    public void invalid_refreshToken() throws Exception {
        //given
        given(jwtService.extractAccessToken(any()))
                .willReturn(Optional.of("Test Access"));
        given(jwtService.extractRefreshToken(any()))
                .willReturn(Optional.of("Test Refresh"));
        given(jwtService.isTokenValid(any()))
                .willThrow(InvalidRequestException.class);
        //when & then
        assertThrows(InvalidRequestException.class,
                () -> filter.doFilterInternal(request, response, filterChain));
    }

    @Test
    public void valid_refreshToken() throws Exception {
        //given
        given(jwtService.extractAccessToken(any()))
                .willReturn(Optional.of("test access"));
        given(jwtService.extractRefreshToken(any()))
                .willReturn(Optional.of("test refresh"));
        User testUser = User.builder()
                .email("test user")
                .nickname("test user")
                .profileImage("test image")
                .role(Role.USER)
                .build();
        given(userService.findByEmail(any()))
                .willReturn(testUser);

        //when
        filter.doFilterInternal(request, response, filterChain);
        // then
        SecurityUserDto userDto = (SecurityUserDto) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        assertThat(testUser.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(testUser.getNickname()).isEqualTo(userDto.getNickname());
        assertThat(testUser.getRole()).isEqualTo(userDto.getRole());
        assertThat(testUser.getProfileImage()).isEqualTo(userDto.getProfileImage());
    }

}