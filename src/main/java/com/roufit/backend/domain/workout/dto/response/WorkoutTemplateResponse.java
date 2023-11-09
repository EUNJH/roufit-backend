package com.roufit.backend.domain.workout.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutTemplateResponse {

    private Long id;
    private String templateName;
    private String recentPerformDate;
    List<SetTemplateResponse> setTemplateResponses = new ArrayList<>();

    @Builder
    public WorkoutTemplateResponse(Long id, String templateName,
                                   LocalDate recentPerformDate,
                                   List<SetTemplateResponse> setTemplateResponses) {
        this.id = id;
        this.templateName = templateName;
        this.recentPerformDate = recentPerformDate.toString();
        this.setTemplateResponses.addAll(setTemplateResponses);
    }
}
