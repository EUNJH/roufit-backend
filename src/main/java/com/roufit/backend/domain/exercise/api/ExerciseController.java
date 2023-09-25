package com.roufit.backend.domain.exercise.api;

import com.roufit.backend.domain.exercise.application.ExerciseFindService;
import com.roufit.backend.domain.exercise.application.ExerciseService;
import com.roufit.backend.domain.exercise.dto.request.ExerciseRequest;
import com.roufit.backend.domain.exercise.dto.response.ExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final ExerciseFindService exerciseFindService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated ExerciseRequest dto) {
        exerciseService.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ExerciseResponse>> getUnderCategory(@PathVariable Long categoryId) {
        List<ExerciseResponse> exercises = exerciseFindService.findByCategory(categoryId);
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    @DeleteMapping("/{exerciseName}")
    public ResponseEntity<?> delete(@PathVariable String exerciseName) {
        exerciseService.delete(exerciseName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
