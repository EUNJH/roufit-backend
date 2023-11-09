package com.roufit.backend.global.security.jwt;

import com.roufit.backend.global.error.exception.InvalidRequestException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class JwtServiceTest {
    private final JwtService jwtService;
    public final String email;

    public JwtServiceTest() {
        this.jwtService = new MockJwtService().jwtService;
        this.email = "ejh@gmail.com";
    }

    @Test
    public void 토큰_생성() throws Exception {
        //given
        String token = jwtService.generateToken(email);
        //when
        String subject = jwtService.extractSubject(token);
        //then
        assertThat(subject).isEqualTo(email);
        assertThat(jwtService.isTokenValid(token)).isTrue();
    }

    @Test
    public void 리프레시토큰_생성() throws Exception {
        //given
        String token = jwtService.generateRefreshToken(email);
        //when
        String subject = jwtService.extractSubject(token);
        //then
        assertThat(subject).isEqualTo(email);
        assertThat(jwtService.isTokenValid(token)).isTrue();
    }

    @Test
    public void 토큰_만료검증() throws Exception {
        //given
        String token = jwtService.buildToken(new HashMap<>(), email, 0);
        //when & then
        assertThrows(InvalidRequestException.class, () -> jwtService.isTokenValid(token));
    }

    @Test
    public void 토큰_키검증() throws Exception {
        //given
        JwtService testService =
                new MockJwtService("nmPJ1F3J/tgjE8AlR5/ZrH51uBUmKvmynHt4SpakJFQ=") //test1
                        .jwtService;
        String token = jwtService.buildToken(new HashMap<>(), email, 0);
        //when & then
        assertThrows(InvalidRequestException.class, () -> testService.isTokenValid(token));
    }

    @Test
    public void 응답헤더_토큰추가() throws Exception {
        //given
        MockHttpServletResponse response = new MockHttpServletResponse();
        String access = jwtService.generateToken(email);
        String refresh = jwtService.generateRefreshToken(email);
        //when
        jwtService.sendAccessAndRefreshToken(response, access, refresh);
        //then
        assertThat(response.getHeader(jwtService.getAccessHeader()))
                .isEqualTo("Bearer " + access);
        assertThat(response.getHeader(jwtService.getRefreshHeader()))
                .isEqualTo("Bearer " + refresh);
    }

    @Test
    public void 요청헤더_토큰추출() throws Exception {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        String access = jwtService.generateToken(email);
        String refresh = jwtService.generateRefreshToken(email);
        //when
        request.addHeader(jwtService.getAccessHeader(), "Bearer " + access);
        request.addHeader(jwtService.getRefreshHeader(), "Bearer " + refresh);
        //then
        assertThat(jwtService.extractAccessToken(request))
                .isEqualTo(Optional.of(access));
        assertThat(jwtService.extractRefreshToken(request))
                .isEqualTo(Optional.of(refresh));
    }

    @Test
    public void 잘못된요청헤더_토큰추출() throws Exception {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        String access = jwtService.generateToken(email);
        String refresh = jwtService.generateRefreshToken(email);
        //when
        request.addHeader(jwtService.getAccessHeader(), access);
        request.addHeader(jwtService.getRefreshHeader(), refresh);
        //then
        assertThat(jwtService.extractAccessToken(request))
                .isEqualTo(Optional.empty());
        assertThat(jwtService.extractRefreshToken(request))
                .isEqualTo(Optional.empty());
    }

}