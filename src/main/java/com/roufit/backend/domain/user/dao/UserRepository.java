package com.roufit.backend.domain.user.dao;

import com.roufit.backend.domain.user.domain.SocialType;
import com.roufit.backend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
