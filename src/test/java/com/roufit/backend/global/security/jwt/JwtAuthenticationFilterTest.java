package com.roufit.backend.global.security.jwt;

import com.roufit.backend.domain.user.application.TokenService;
import com.roufit.backend.domain.user.application.UserService;
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

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {
    @InjectMocks
    private JwtAuthenticationFilter filter;
    @Mock
    private TokenService tokenService;
    @Mock
    private UserService userService;
    private final JwtService jwtService;
    MockFilterChain filterChain;
    MockHttpServletRequest request;
    MockHttpServletResponse response;

    public JwtAuthenticationFilterTest() {
        this.jwtService = new MockJwtService().jwtService;
        filter = new JwtAuthenticationFilter(jwtService, userService, tokenService);
    }

    @BeforeEach
    public void init() {
        this.filterChain = new MockFilterChain();
        this.request = new MockHttpServletRequest();
        this.response = new MockHttpServletResponse();
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
        String access = jwtService.buildToken(new HashMap<>(), "abc", 0);
        request.addHeader(jwtService.getAccessHeader(), "Bearer " + access);

        //when & then
        assertThrows(InvalidRequestException.class,
                () -> filter.doFilterInternal(request, response, filterChain));
    }

    @Test
    public void invalid_refreshToken() throws Exception {
        //given
        String access = jwtService.buildToken(new HashMap<>(), "abc", 0);
        String refresh = jwtService.buildToken(new HashMap<>(), "abc", 0);
        request.addHeader(jwtService.getAccessHeader(), "Bearer " + access);
        request.addHeader(jwtService.getRefreshHeader(), "Bearer " + refresh);

        //when & then
        assertThrows(InvalidRequestException.class,
                () -> filter.doFilterInternal(request, response, filterChain));
    }

}