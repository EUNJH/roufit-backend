package com.roufit.backend.domain.exercise.dto.request;

import com.roufit.backend.domain.exercise.domain.exercise.Equipment;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.exercise.domain.exercise.ExerciseType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(description = "운동 요청")
@Data
public class ExerciseRequest {

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private Equipment equipment;

    @NotBlank
    private ExerciseType type;

    @PositiveOrZero
    private Long category;

    public Exercise toEntity() {
        return Exercise.builder()
                .name(name)
                .description(description)
                .equipment(equipment)
                .type(type)
                .build();
    }
}
