package com.roufit.backend.domain.workout.dto.request;

import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.workout.domain.SetTemplate;
import com.roufit.backend.domain.workout.domain.WorkoutTemplate;
import lombok.Data;

import java.util.List;

@Data
public class WorkoutTemplateRequest {

    private String templateName;
    private List<SetTemplateRequest> setTemplates;

    public WorkoutTemplate toEntity(User user) {
        return WorkoutTemplate.builder()
                .templateName(templateName)
                .build();
    }
}
