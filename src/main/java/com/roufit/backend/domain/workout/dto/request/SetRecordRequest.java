package com.roufit.backend.domain.workout.dto.request;

import com.roufit.backend.domain.workout.domain.record.SetRecord;
import com.roufit.backend.domain.workout.domain.record.WorkoutRecord;
import com.roufit.backend.domain.workout.domain.template.SetTemplate;
import lombok.*;

@Data
public class SetRecordRequest {

    private Long setTemplateId;
    private boolean isCompleted;
    private boolean isSuccess;
}
