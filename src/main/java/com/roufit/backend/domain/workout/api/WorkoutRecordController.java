package com.roufit.backend.domain.workout.api;

import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.domain.workout.application.record.WorkoutRecordService;
import com.roufit.backend.domain.workout.dto.request.WorkoutRecordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/workout/record")
public class WorkoutRecordController {

    private final WorkoutRecordService workoutRecordService;

    @PostMapping
    public ResponseEntity<?> saveRecord(WorkoutRecordRequest request,
                                        @AuthenticationPrincipal SecurityUserDto userDto) {
        workoutRecordService.create(request, userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
