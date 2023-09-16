package com.roufit.backend.domain.workout.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class WorkoutTemplateResponse {

    private Long id;
    private String templateName;
    private LocalDateTime recentPerformDate;
    List<SetTemplateResponse> setTemplateResponses = new ArrayList<>();

}
