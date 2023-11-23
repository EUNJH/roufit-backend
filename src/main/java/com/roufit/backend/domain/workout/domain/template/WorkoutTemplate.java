package com.roufit.backend.domain.workout.domain.template;

import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.workout.dto.response.SetTemplateResponse;
import com.roufit.backend.domain.workout.dto.response.WorkoutTemplateResponse;
import com.roufit.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "workout_template")
public class WorkoutTemplate extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_template_id")
    private Long id;

    private String templateName;

    private LocalDate recentPerformDate;

    @OneToMany(mappedBy = "workoutTemplate")
    private List<SetTemplate> setTemplates = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Builder
    public WorkoutTemplate(String templateName, User user) {
        this.templateName = templateName;
        this.user = user;
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

    public void updatePerformDate(LocalDateTime startDate) {
        this.recentPerformDate = startDate.toLocalDate();
    }

    public void addSet(List<SetTemplate> setTemplate) {
        setTemplates.addAll(setTemplate);
    }
}
