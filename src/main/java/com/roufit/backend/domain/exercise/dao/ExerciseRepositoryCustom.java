package com.roufit.backend.domain.exercise.dao;

import com.roufit.backend.domain.exercise.domain.exercise.Exercise;

import java.util.List;

public interface ExerciseRepositoryCustom {
    List<Exercise> findByCategory(Long categoryId);
}
