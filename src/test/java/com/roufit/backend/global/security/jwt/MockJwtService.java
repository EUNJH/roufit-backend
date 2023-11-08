package com.roufit.backend.global.security.jwt;

public class MockJwtService {
    public JwtService jwtService;
    public String testKey = "iM0hCLU0fZc885zfkFPX3UJwSHbYyam9ji0WglnT3fc="; //test
    public long accessExpiration = 3600000; //1시간
    public long refreshExpiration = 86400000; //1일
    public String accessHeader = "Authorization";
    public String refreshHeader = "Authorization-refresh";

    public MockJwtService() {
        this.jwtService = new JwtService(
                testKey,
                accessExpiration, refreshExpiration,
                accessHeader, refreshHeader
        );
    }

    public MockJwtService(String key) {
        this.jwtService = new JwtService(
                key,
                accessExpiration, refreshExpiration,
                accessHeader, refreshHeader
        );
    }
}
