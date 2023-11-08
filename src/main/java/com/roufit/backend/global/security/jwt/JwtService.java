package com.roufit.backend.global.security.jwt;

import com.roufit.backend.global.error.exception.ErrorCode;
import com.roufit.backend.global.error.exception.InvalidRequestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Getter
@RequiredArgsConstructor
@Service
public class JwtService {


    private String SECRET_KEY;
    private long accessExpiration;
    private long refreshExpiration;
    private String accessHeader;
    private String refreshHeader;

    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";

    public JwtService(@Value("${jwt.secret-key}") String SECRET_KEY,
                      @Value("${jwt.access.expiration}") long accessExpiration,
                      @Value("${jwt.refresh.expiration}") long refreshExpiration,
                      @Value("${jwt.access.header}") String accessHeader,
                      @Value("${jwt.refresh.header}") String refreshHeader) {
        this.SECRET_KEY = SECRET_KEY;
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.accessHeader = accessHeader;
        this.refreshHeader = refreshHeader;
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(String email) {
        return buildToken(new HashMap<>(), email, accessExpiration);
    }

    public String generateRefreshToken(String email) {
        return buildToken(new HashMap<>(), email, refreshExpiration);
    }

    public String buildToken(
            Map<String, Object> extraClaims,
            String email,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e){
            throw new InvalidRequestException(ErrorCode.EXPIRED_TOKEN);
        } catch (MalformedJwtException e){
            throw new InvalidRequestException(ErrorCode.MALFORMED_TOKEN);
        } catch (SignatureException e){
            throw new InvalidRequestException(ErrorCode.MISMATCH_TOKEN_SIGNATURE);
        }

    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response,
                                          String accessToken,
                                          String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
    }

    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        log.info("Access Token 헤더 설정");
        response.setHeader(accessHeader, BEARER + accessToken);
    }

    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        log.info("Refresh Token 헤더 설정");
        response.setHeader(refreshHeader, BEARER + refreshToken);
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }
}
