package com.roufit.backend.domain.exercise.application;

import com.roufit.backend.domain.exercise.dao.exercise.ExerciseCategoryRepository;
import com.roufit.backend.domain.exercise.dao.exercise.ExerciseRepository;
import com.roufit.backend.domain.exercise.domain.category.Category;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.exercise.domain.exercise.ExerciseCategory;
import com.roufit.backend.domain.exercise.dto.request.ExerciseRequest;

import com.roufit.backend.domain.exercise.dto.response.ExerciseResponse;
import com.roufit.backend.global.error.exception.DuplicateException;
import com.roufit.backend.global.error.exception.EntityNotFoundException;
import com.roufit.backend.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseCategoryRepository exerciseCategoryRepository;
    private final CategoryService categoryService;

    @Transactional
    public ExerciseResponse create(ExerciseRequest dto) {
        if(exerciseRepository.existsByName(dto.getName())) {
            throw new DuplicateException(dto.getName(), ErrorCode.EXERCISE_NAME_DUPLICATION);
        }

        Exercise newExercise = dto.toEntity();
        exerciseRepository.save(newExercise);

        List<ExerciseCategory> exerciseCategories = mappingCategories(newExercise, dto.getCategory());
        exerciseCategoryRepository.saveAll(exerciseCategories);
        return ExerciseResponse.toDto(exerciseCategories);
    }

    private List<ExerciseCategory> mappingCategories(Exercise newExercise, Long categoryId) {
        List<Category> categories = categoryService.getAllById(categoryId);
        return categories.stream()
                        .map(category -> ExerciseCategory.builder()
                                .exercise(newExercise)
                                .category(category)
                                .build())
                        .toList();
    }

    @Transactional
    public Exercise delete(String exerciseName) {
        Exercise exercise = exerciseRepository.findByName(exerciseName)
                .orElseThrow(() -> new EntityNotFoundException(exerciseName, ErrorCode.EXERCISE_NAME_DUPLICATION));
        exercise.remove();
        return exercise;
    }

}
