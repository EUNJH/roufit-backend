package com.roufit.backend.domain.member.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.roufit.backend.domain.member.domain.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.roufit.backend.domain.member.domain.QToken.token;
import static com.roufit.backend.domain.member.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class TokenRepositoryImpl implements TokenRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public Token findByUserEmail(String email) {
        return jpaQueryFactory.selectFrom(token)
                .join(token.user, user)
                .where(user.email.eq(email))
                .fetchOne();
    }


}
