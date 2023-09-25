package com.roufit.backend.domain.workout.dto.request;

import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.workout.domain.record.SetRecord;
import com.roufit.backend.domain.workout.domain.record.WorkoutRecord;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "워크아웃 기록 요청(이름)")
@Data
public class WorkoutRecordRequest {

    @NotNull
    @PositiveOrZero
    private Long workoutId;

    @Positive
    private Integer duration;

    @NotNull
    @Past
    private LocalDateTime startTime;

    @NotNull
    @PastOrPresent
    private LocalDateTime endTime;

    @NotNull
    private Boolean isCompleted;

    List<SetRecordRequest> setRecordRequests = new ArrayList<>();

    public List<Long> getAllSetTemplateIds() {
        return setRecordRequests.stream()
                .map(SetRecordRequest::getSetTemplateId)
                .toList();
    }
}
