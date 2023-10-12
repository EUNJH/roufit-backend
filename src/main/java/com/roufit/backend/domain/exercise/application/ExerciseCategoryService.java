package com.roufit.backend.domain.exercise.application;

import com.roufit.backend.domain.exercise.dao.exercise.ExerciseCategoryRepository;
import com.roufit.backend.domain.exercise.domain.category.Category;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.exercise.domain.exercise.ExerciseCategory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExerciseCategoryService {

    private final ExerciseCategoryRepository exerciseCategoryRepository;
    private final CategoryService categoryService;

    @Transactional
    public void create(Exercise newExercise, Long categoryId) {
        List<Category> categories = categoryService.getAllById(categoryId);
        List<ExerciseCategory> exerciseCategories =
                categories.stream()
                        .map(category -> ExerciseCategory.builder()
                                .exercise(newExercise)
                                .category(category)
                                .build())
                        .toList();
        exerciseCategoryRepository.saveAll(exerciseCategories);
    }
}
