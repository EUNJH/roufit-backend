package com.roufit.backend.domain.exercise.api;

import com.roufit.backend.domain.exercise.application.ExerciseFindService;
import com.roufit.backend.domain.exercise.application.ExerciseService;
import com.roufit.backend.domain.exercise.dto.request.ExerciseRequest;
import com.roufit.backend.domain.exercise.dto.response.ExerciseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Exercise", description = "운동 종목 API 문서")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final ExerciseFindService exerciseFindService;

    @Operation(summary = "운동 종목 생성")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated ExerciseRequest dto) {
        exerciseService.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "해당 카테고리 운동 조회")
    @GetMapping
    public ResponseEntity<List<ExerciseResponse>> getUnderCategory(@RequestParam Long category) {
        List<ExerciseResponse> exercises = exerciseFindService.findByCategory(category);
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    @Operation(summary = "운동 이름으로 삭제")
    @DeleteMapping("/{exerciseName}")
    public ResponseEntity<?> delete(@PathVariable String exerciseName) {
        exerciseService.delete(exerciseName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
