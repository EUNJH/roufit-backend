package com.roufit.backend.domain.workout.dto.request;

import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Schema(description = "워크아웃 템플릿 요청(이름)")
@Builder
@Data
public class WorkoutTemplateRequest {

    private String templateName;
    private List<SetTemplateRequest> setTemplates;

}
