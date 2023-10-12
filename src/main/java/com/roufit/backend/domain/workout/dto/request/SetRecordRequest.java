package com.roufit.backend.domain.workout.dto.request;

import com.roufit.backend.domain.workout.domain.record.SetRecord;
import com.roufit.backend.domain.workout.domain.record.WorkoutRecord;
import com.roufit.backend.domain.workout.domain.template.SetTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Schema(description = "워크아웃 기록 요청(세트)")
@Data
public class SetRecordRequest {

    @NotNull
    @PositiveOrZero
    private Long setTemplateId;

    @NotNull
    private Boolean isCompleted;

    @NotNull
    private Boolean isSuccess;
}
