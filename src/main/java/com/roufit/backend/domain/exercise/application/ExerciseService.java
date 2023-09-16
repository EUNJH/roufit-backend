package com.roufit.backend.domain.exercise.application;

import com.roufit.backend.domain.exercise.dao.ExerciseRepository;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.exercise.dto.request.ExerciseRequest;
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
        Exercise newExercise = dto.getEntity();
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
}
