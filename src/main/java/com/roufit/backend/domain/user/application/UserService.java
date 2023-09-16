package com.roufit.backend.domain.user.application;

import com.roufit.backend.domain.user.dao.UserRepository;
import com.roufit.backend.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);
    }
}
