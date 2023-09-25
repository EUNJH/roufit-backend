package com.roufit.backend.domain.workout.dto.request;

import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.workout.domain.record.SetRecord;
import com.roufit.backend.domain.workout.domain.record.WorkoutRecord;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class WorkoutRecordRequest {

    private Long workoutId;
    private Integer duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isCompleted;

    List<SetRecordRequest> setRecordRequests = new ArrayList<>();

    public List<Long> getAllSetTemplateIds() {
        return setRecordRequests.stream()
                .map(SetRecordRequest::getSetTemplateId)
                .toList();
    }
}
