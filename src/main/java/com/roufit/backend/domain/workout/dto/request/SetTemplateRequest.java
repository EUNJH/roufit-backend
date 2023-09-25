package com.roufit.backend.domain.workout.dto.request;

import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.workout.domain.template.SetTemplate;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import lombok.Data;

@Data
public class SetTemplateRequest {

    private Long exerciseId;

    private int setCount;
    private int restPeriod;
    private int goalRepetition;
    private int goalTime;
    private int increaseOffset;
    private int additionalWeight;
}
