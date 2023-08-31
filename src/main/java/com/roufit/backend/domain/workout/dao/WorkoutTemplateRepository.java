package com.roufit.backend.domain.workout.dao;

import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate, Long> {
}
