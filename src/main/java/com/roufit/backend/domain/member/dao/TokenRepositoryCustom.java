package com.roufit.backend.domain.member.dao;

import com.roufit.backend.domain.member.domain.Token;

public interface TokenRepositoryCustom {

    Token findByUserEmail(String email);
}
