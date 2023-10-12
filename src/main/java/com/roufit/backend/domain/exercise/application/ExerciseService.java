package com.roufit.backend.domain.exercise.application;

import com.roufit.backend.domain.exercise.dao.exercise.ExerciseRepository;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.exercise.dto.request.ExerciseRequest;

import com.roufit.backend.global.error.exception.DuplicateException;
import com.roufit.backend.global.error.exception.EntityNotFoundException;
import com.roufit.backend.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseCategoryService exerciseCategoryService;

    @Transactional
    public void create(ExerciseRequest dto) {
        if(exerciseRepository.existsByName(dto.getName())) {
            throw new DuplicateException(dto.getName(), ErrorCode.EXERCISE_NAME_DUPLICATION);
        }

        Exercise newExercise = dto.toEntity();
        exerciseRepository.save(newExercise);
        exerciseCategoryService.create(newExercise, dto.getCategory());
    }

    @Transactional
    public void delete(String exerciseName) {
        Exercise exercise = exerciseRepository.findByName(exerciseName)
                .orElseThrow(() -> new EntityNotFoundException(exerciseName, ErrorCode.EXERCISE_NAME_DUPLICATION));
        exercise.remove();
    }

}
