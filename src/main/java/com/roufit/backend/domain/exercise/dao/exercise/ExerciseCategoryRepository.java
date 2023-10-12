package com.roufit.backend.domain.exercise.dao.exercise;

import com.roufit.backend.domain.exercise.domain.exercise.ExerciseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseCategoryRepository extends JpaRepository<ExerciseCategory, Long> {
}
