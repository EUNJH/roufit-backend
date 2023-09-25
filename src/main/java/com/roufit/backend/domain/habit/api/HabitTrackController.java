package com.roufit.backend.domain.habit.api;

import com.roufit.backend.domain.habit.application.HabitTrackService;
import com.roufit.backend.domain.habit.dto.HabitResponse;
import com.roufit.backend.domain.user.dto.SecurityUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/habit-track")
public class HabitTrackController {

    private final HabitTrackService habitTrackService;

    @GetMapping
    public ResponseEntity<List<HabitResponse>> getByDate(@RequestParam Integer year,
                                                         @RequestParam Integer month,
                                                         @AuthenticationPrincipal SecurityUserDto userDto) {
        List<HabitResponse> habitResponses = habitTrackService.getByDate(year, month, userDto);
        return new ResponseEntity<>(habitResponses, HttpStatus.OK);
    }
}
