package com.roufit.backend.domain.workout.dto.response;

import com.roufit.backend.domain.workout.domain.record.SetRecord;
import com.roufit.backend.domain.workout.domain.record.WorkoutRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class WorkoutRecordResponse {
    private Long id;
    private Integer duration;
    private LocalDateTime startTime;
    private Boolean isCompleted;
    List<SetRecordResponse> setRecordResponses = new ArrayList<>();

    @Builder
    public WorkoutRecordResponse(WorkoutRecord workoutRecord) {
        this.id = workoutRecord.getId();
        this.duration = workoutRecord.getDuration();
        this.startTime = workoutRecord.getStartTime();
        this.isCompleted = workoutRecord.getIsCompleted();
        workoutRecord.getSetRecords()
                .forEach(s -> setRecordResponses.add(
                        SetRecordResponse.builder().setRecord(s).build()
                ));
    }
}
