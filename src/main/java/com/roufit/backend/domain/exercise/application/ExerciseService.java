package com.roufit.backend.domain.exercise.application;

import com.roufit.backend.domain.exercise.dao.ExerciseRepository;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.exercise.dto.request.ExerciseRequest;
import com.roufit.backend.domain.exercise.dto.response.ExerciseResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseCategoryService exerciseCategoryService;

    @Transactional
    public void create(ExerciseRequest dto) {
        if(exerciseRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException(); //TODO 이미 존재하는 운동
        }

        Exercise newExercise = dto.toEntity();
        exerciseRepository.save(newExercise);
        exerciseCategoryService.create(newExercise, dto.getCategory());
    }

    public List<Exercise> findByIds(List<Long> exerciseIds) {
        List<Exercise> exercises = exerciseRepository.findByIdIn(exerciseIds);
        if(exerciseIds.size() != exercises.size()) {
            throw new NoSuchElementException();
        }

        return exercises;
    }

    public List<ExerciseResponse> findByCategory(Long categoryId) {
        List<Exercise> exercises = exerciseRepository.findByCategory(categoryId);
        return exercises.stream()
                .map(Exercise::toDTO)
                .toList();
    }
}
