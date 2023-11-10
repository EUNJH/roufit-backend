package com.roufit.backend.domain.user.application;

import com.roufit.backend.domain.user.dao.UserRepository;
import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.domain.user.dto.UserResponse;
import com.roufit.backend.global.error.exception.EntityNotFoundException;
import com.roufit.backend.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponse find(SecurityUserDto userDto) {
        return UserResponse.toDto(findByEmail(userDto.getEmail()));
    }

    public User findByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(email, ErrorCode.USER_EMAIL_NOT_FOUND));
    }

    public User getReferenceById(final Long id) {
        return userRepository.getReferenceById(id);
    }
}
