package com.roufit.backend.domain.exercise.dao;

import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByIdIn(List<Long> ids);
}
