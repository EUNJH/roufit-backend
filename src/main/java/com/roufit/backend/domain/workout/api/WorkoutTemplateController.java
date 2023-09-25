package com.roufit.backend.domain.workout.api;

import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.domain.workout.application.template.WorkoutTemplateService;
import com.roufit.backend.domain.workout.dto.response.WorkoutTemplateResponse;
import com.roufit.backend.domain.workout.dto.request.WorkoutTemplateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/workout/template")
public class WorkoutTemplateController {

    private final WorkoutTemplateService workoutTemplateService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated WorkoutTemplateRequest request,
                                    @AuthenticationPrincipal SecurityUserDto userDto) {

        workoutTemplateService.create(request, userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<WorkoutTemplateResponse> get(
            @AuthenticationPrincipal SecurityUserDto userDto
    ) {
        return new ResponseEntity<>(
                workoutTemplateService.findByUser(userDto),
                HttpStatus.OK
        );
    }
}
