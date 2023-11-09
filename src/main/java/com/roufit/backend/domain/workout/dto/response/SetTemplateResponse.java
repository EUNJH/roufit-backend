package com.roufit.backend.domain.workout.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetTemplateResponse {

    private Long id;
    private int setCount;
    private int restPeriod;
    private Integer goalRepetition;
    private Integer goalTime;
    private int additionalWeight;

}
