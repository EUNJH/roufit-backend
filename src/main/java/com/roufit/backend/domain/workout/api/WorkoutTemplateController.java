package com.roufit.backend.domain.workout.api;

import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.domain.workout.application.template.WorkoutTemplateService;
import com.roufit.backend.domain.workout.dto.response.WorkoutTemplateResponse;
import com.roufit.backend.domain.workout.dto.request.WorkoutTemplateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Workout Template", description = "워크아웃 템플릿 API 문서")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/workout/template")
public class WorkoutTemplateController {

    private final WorkoutTemplateService workoutTemplateService;

    @Operation(summary = "템플릿 생성")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated WorkoutTemplateRequest request,
                                    @AuthenticationPrincipal SecurityUserDto userDto) {

        workoutTemplateService.create(request, userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "유저 템플릿 조회")
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
