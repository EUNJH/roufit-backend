package com.roufit.backend.domain.user.api;

import com.roufit.backend.domain.user.application.UserService;
import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.domain.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "User", description = "회원 API 문서")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 정보 요청")
    @GetMapping
    public ResponseEntity<UserResponse> getByDate(@AuthenticationPrincipal SecurityUserDto userDto) {
        UserResponse userResponse = userService.find(userDto);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
