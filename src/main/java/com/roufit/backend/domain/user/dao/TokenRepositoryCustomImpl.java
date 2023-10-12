package com.roufit.backend.domain.user.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.roufit.backend.domain.user.domain.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.roufit.backend.domain.user.domain.QToken.token;
import static com.roufit.backend.domain.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class TokenRepositoryCustomImpl implements TokenRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public Token findByUserEmail(String email) {
        return jpaQueryFactory.selectFrom(token)
                .join(token.user, user)
                .where(user.email.eq(email))
                .fetchOne();
    }


}
