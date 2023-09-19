package com.roufit.backend.domain.exercise.dao;

import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.global.common.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long>, ExerciseRepositoryCustom {
    List<Exercise> findByIdIn(List<Long> ids);
    List<Exercise> findByIdInAndStatus(List<Long> ids, Status status);
    boolean existsByName(String name);
    Optional<Exercise> findByName(String name);
}
