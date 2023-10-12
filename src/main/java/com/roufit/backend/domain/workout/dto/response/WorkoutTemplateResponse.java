package com.roufit.backend.domain.workout.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class WorkoutTemplateResponse {

    private Long id;
    private String templateName;
    private LocalDate recentPerformDate;
    List<SetTemplateResponse> setTemplateResponses = new ArrayList<>();

    @Builder
    public WorkoutTemplateResponse(Long id, String templateName,
                                   LocalDate recentPerformDate,
                                   List<SetTemplateResponse> setTemplateResponses) {
        this.id = id;
        this.templateName = templateName;
        this.recentPerformDate = recentPerformDate;
        this.setTemplateResponses.addAll(setTemplateResponses);
    }
}
