package com.roufit.backend.domain.exercise.dto.request;

import com.roufit.backend.domain.exercise.domain.exercise.Equipment;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.exercise.domain.exercise.ExerciseType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Schema(description = "운동 요청")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Equipment equipment;

    @NotNull
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
