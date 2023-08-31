package com.roufit.backend.domain.workout.dto.request;

import com.roufit.backend.domain.member.domain.Member;
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
    private long duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isCompleted;

    List<SetRecordRequest> setRecordRequests = new ArrayList<>();

    public WorkoutRecord getWorkoutRecord(Member member, WorkoutTemplate workoutTemplate) {
        return WorkoutRecord.builder()
                .member(member)
                .workoutTemplate(workoutTemplate)
                .duration(duration)
                .startTime(startTime)
                .endTime(endTime)
                .isCompleted(isCompleted)
                .build();
    }
}
