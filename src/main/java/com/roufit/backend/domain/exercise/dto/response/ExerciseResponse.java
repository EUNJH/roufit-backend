package com.roufit.backend.domain.exercise.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class ExerciseResponse {

    private String name;
    private String description;
    private String equipment;
    private String type;

    @Builder
    public ExerciseResponse(String name, String description,
                            String equipment, String type) {
        this.name = name;
        this.description = description;
        this.equipment = equipment;
        this.type = type;
    }
}
