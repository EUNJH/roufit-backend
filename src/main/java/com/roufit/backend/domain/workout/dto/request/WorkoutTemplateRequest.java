package com.roufit.backend.domain.workout.dto.request;

import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import lombok.Data;

import java.util.List;

@Data
public class WorkoutTemplateRequest {

    private String templateName;
    private List<SetTemplateRequest> setTemplates;

}
