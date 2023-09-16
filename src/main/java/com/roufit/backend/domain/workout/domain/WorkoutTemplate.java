package com.roufit.backend.domain.workout.domain;

import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.workout.dto.response.SetTemplateResponse;
import com.roufit.backend.domain.workout.dto.response.WorkoutTemplateResponse;
import com.roufit.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "workout_template")
public class WorkoutTemplate extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "workout_template_id")
    private Long id;

    private String templateName;

    private LocalDateTime recentPerformDate;

    @OneToMany(mappedBy = "workoutTemplate")
    private List<SetTemplate> setTemplates = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public WorkoutTemplate(String templateName, LocalDateTime recentPerformDate) {
        this.templateName = templateName;
        this.recentPerformDate = recentPerformDate;
    }

    public WorkoutTemplateResponse toDto() {
        List<SetTemplateResponse> setTemplateResponses = setTemplates.stream()
                .map(SetTemplate::toDto)
                .toList();
        return WorkoutTemplateResponse.builder()
                .templateName(templateName)
                .recentPerformDate(recentPerformDate)
                .setTemplateResponses(setTemplateResponses)
                .build();
    }
}
