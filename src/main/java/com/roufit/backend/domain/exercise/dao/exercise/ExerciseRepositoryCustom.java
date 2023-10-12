package com.roufit.backend.domain.exercise.dao.exercise;

import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.global.common.Status;

import java.util.List;

public interface ExerciseRepositoryCustom {
    List<Exercise> findByCategoryAndStatus(Long categoryId);
}
