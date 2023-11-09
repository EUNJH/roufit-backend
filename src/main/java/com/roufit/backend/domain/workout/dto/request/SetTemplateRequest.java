package com.roufit.backend.domain.workout.dto.request;

import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.workout.domain.template.SetTemplate;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Schema(description = "워크아웃 템플릿 요청(세트)")
@Builder
@Data
public class SetTemplateRequest {

    @PositiveOrZero
    private Long exerciseId;

    @NotNull
    @Positive
    private Integer setCount;

    @NotNull
    @Min(value = 30)
    @Max(value = 120)
    private Integer restPeriod;

    @Min(value = 5)
    @Max(value = 15)
    private Integer goalRepetition;

    private Integer goalTime;

    @NotNull
    @Positive
    private Integer increaseOffset;

    private int additionalWeight;
}
