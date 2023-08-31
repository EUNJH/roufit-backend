package com.roufit.backend.domain.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialType {

    GOOGLE("GOOGLE"), NAVER("NAVER");

    private final String socialName;

    public static SocialType getSocialType(String registrationId) {
        if(NAVER.socialName.equals(registrationId.toUpperCase())) {
            return SocialType.NAVER;
        }
        return SocialType.GOOGLE;
    }
}
