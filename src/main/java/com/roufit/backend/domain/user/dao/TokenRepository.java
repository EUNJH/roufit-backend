package com.roufit.backend.domain.user.dao;

import com.roufit.backend.domain.user.domain.Token;
import com.roufit.backend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long>, TokenRepositoryCustom{

    Optional<Token> findByUser(User user);
}
