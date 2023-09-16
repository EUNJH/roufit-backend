package com.roufit.backend.domain.workout.dto.request;

import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.workout.domain.SetTemplate;
import com.roufit.backend.domain.workout.domain.WorkoutTemplate;
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

    public SetTemplate toEntity(WorkoutTemplate workoutTemplate,
                                Exercise exercise
    ) {
        return SetTemplate.builder()
                .workoutTemplate(workoutTemplate)
                .exercise(exercise)
                .setCount(setCount)
                .restPeriod(restPeriod)
                .goalRepetition(goalRepetition)
                .goalTime(goalTime)
                .increaseOffset(increaseOffset)
                .additionalWeight(additionalWeight)
                .build();
    }
}
