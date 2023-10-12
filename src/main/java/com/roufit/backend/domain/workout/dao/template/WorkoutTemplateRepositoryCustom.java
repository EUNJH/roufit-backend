package com.roufit.backend.domain.workout.dao.template;

import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;

public interface WorkoutTemplateRepositoryCustom {
    WorkoutTemplate findTemplateAndSetById(Long userId);
}
