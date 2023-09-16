package com.roufit.backend.domain.workout.dto.request;

import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.workout.domain.WorkoutRecord;
import com.roufit.backend.domain.workout.domain.WorkoutTemplate;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class WorkoutRecordRequest {

    private Long workoutId;
    private long duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isCompleted;

    List<SetRecordRequest> setRecordRequests = new ArrayList<>();

    public WorkoutRecord getWorkoutRecord(User user, WorkoutTemplate workoutTemplate) {
        return WorkoutRecord.builder()
                .user(user)
                .workoutTemplate(workoutTemplate)
                .duration(duration)
                .startTime(startTime)
                .endTime(endTime)
                .isCompleted(isCompleted)
                .build();
    }
}
