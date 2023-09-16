package com.roufit.backend.domain.user.dao;

import com.roufit.backend.domain.user.domain.Token;

public interface TokenRepositoryCustom {

    Token findByUserEmail(String email);
}
