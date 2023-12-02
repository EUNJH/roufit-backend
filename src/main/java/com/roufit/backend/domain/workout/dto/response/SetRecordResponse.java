package com.roufit.backend.domain.workout.dto.response;

import com.roufit.backend.domain.workout.domain.record.SetRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SetRecordResponse {
    private String exerciseName;
    private Integer setCount;
    private Integer restPeriod;
    private Integer goalRepetition;
    private Integer goalTime;
    private Integer additionalWeight;
    private Boolean isCompleted;
    private Boolean isSuccess;

    @Builder
    public SetRecordResponse(SetRecord setRecord) {
        this.exerciseName = setRecord.getExerciseName();
        this.setCount = setRecord.getSetCount();
        this.restPeriod = setRecord.getRestPeriod();
        this.goalRepetition = setRecord.getGoalRepetition();
        this.goalTime = setRecord.getGoalTime();
        this.additionalWeight = setRecord.getAdditionalWeight();
        this.isCompleted = setRecord.getIsCompleted();
        this.isSuccess = setRecord.getIsSuccess();
    }
}
