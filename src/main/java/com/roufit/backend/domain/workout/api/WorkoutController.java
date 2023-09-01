package com.roufit.backend.domain.workout.api;

import com.roufit.backend.domain.member.domain.User;
import com.roufit.backend.domain.workout.application.WorkoutRecordService;
import com.roufit.backend.domain.workout.dto.request.WorkoutRecordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/workout")
public class WorkoutController {

    private final WorkoutRecordService workoutRecordService;

    @PostMapping
    public ResponseEntity<?> saveRecord(WorkoutRecordRequest request, User user) {
        workoutRecordService.saveWorkoutRecord(request, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
