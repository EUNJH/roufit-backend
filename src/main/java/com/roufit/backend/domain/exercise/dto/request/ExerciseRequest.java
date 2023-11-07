package com.roufit.backend.domain.exercise.dto.request;

import com.roufit.backend.domain.exercise.domain.exercise.Equipment;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.exercise.domain.exercise.ExerciseType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Schema(description = "운동 요청")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseRequest {

    @NotBlank(message = "운동 이름은 빈 값일 수 없습니다.")
    private String name;

    private String description;

    @NotNull(message = "운동 장비는 널 값일 수 없습니다.")
    private Equipment equipment;

    @NotNull(message = "운동 장비는 널 값일 수 없습니다.")
    private ExerciseType type;

    @Positive(message = "카테고리 ID는 양수여야 합니다.")
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
