package com.roufit.backend.domain.exercise.api;

import com.roufit.backend.domain.exercise.application.ExerciseService;
import com.roufit.backend.domain.exercise.dto.request.ExerciseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity<?> create(ExerciseRequest dto) {
        exerciseService.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
