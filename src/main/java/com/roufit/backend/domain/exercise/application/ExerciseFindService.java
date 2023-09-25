package com.roufit.backend.domain.exercise.application;

import com.roufit.backend.domain.exercise.dao.exercise.ExerciseRepository;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.exercise.dto.response.ExerciseResponse;
import com.roufit.backend.global.common.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExerciseFindService {

    private final ExerciseRepository exerciseRepository;

    public List<ExerciseResponse> findByCategory(Long categoryId) {
        List<Exercise> exercises = exerciseRepository.findByCategoryAndStatus(categoryId, Status.ACTIVE);
        return exercises.stream()
                .map(Exercise::toDTO)
                .toList();
    }

    public Exercise getReferenceById(final Long exerciseId) {
        return exerciseRepository.getReferenceById(exerciseId);
    }
}
