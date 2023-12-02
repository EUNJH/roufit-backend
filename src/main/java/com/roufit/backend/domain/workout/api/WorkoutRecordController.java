package com.roufit.backend.domain.workout.api;

import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.domain.workout.application.record.WorkoutRecordService;
import com.roufit.backend.domain.workout.dto.request.WorkoutRecordRequest;
import com.roufit.backend.domain.workout.dto.response.WorkoutRecordResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Workout Record", description = "워크아웃 기록 API 문서")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/workout/record")
public class WorkoutRecordController {

    private final WorkoutRecordService workoutRecordService;

    @Operation(summary = "운동 기록 생성")
    @PostMapping
    public ResponseEntity<?> saveRecord(@RequestBody @Validated WorkoutRecordRequest request,
                                        @AuthenticationPrincipal SecurityUserDto userDto) {
        workoutRecordService.create(request, userDto.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "운동 기록 조회")
    @GetMapping
    public ResponseEntity<?> findByUserId(
            @RequestParam(required = false) Long lastId,
            @AuthenticationPrincipal SecurityUserDto userDto) {
        List<WorkoutRecordResponse> workoutRecordResponses =
                workoutRecordService.findByUserIdWithPagination(lastId, userDto.getId());
        return new ResponseEntity<>(workoutRecordResponses, HttpStatus.OK);
    }
}
