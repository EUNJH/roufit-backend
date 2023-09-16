package com.roufit.backend.domain.workout.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SetTemplateResponse {

    private Long id;
    private int setCount;
    private int restPeriod;
    private int goalRepetition;
    private int goalTime;
    private int additionalWeight;

}
