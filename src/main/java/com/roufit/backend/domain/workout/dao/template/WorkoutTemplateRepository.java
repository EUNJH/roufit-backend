package com.roufit.backend.domain.workout.dao.template;

import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate, Long>, WorkoutTemplateRepositoryCustom {

    Optional<WorkoutTemplate> findByUserId(Long userId);
    Boolean existsByUserId(Long userId);
}
