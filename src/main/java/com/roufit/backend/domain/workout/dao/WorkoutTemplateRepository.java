package com.roufit.backend.domain.workout.dao;

import com.roufit.backend.domain.workout.domain.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate, Long>, WorkoutTemplateRepositoryCustom {

    Optional<WorkoutTemplate> findByIdAndUserId(Long id, Long userId);

    List<WorkoutTemplate> findByUserId(Long userId);
}
