package com.roufit.backend.domain.exercise.dto.request;

import com.roufit.backend.domain.exercise.domain.exercise.Equipment;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.exercise.domain.exercise.ExerciseType;
import lombok.Data;

@Data
public class ExerciseRequest {

    private String name;
    private String description;
    private String equipment;
    private String type;
    private Long category;

    public Exercise getEntity() {
        return Exercise.builder()
                .name(name)
                .description(description)
                .equipment(Equipment.valueOf(equipment))
                .type(ExerciseType.valueOf(type))
                .build();
    }
}
