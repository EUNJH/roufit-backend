package com.roufit.backend.domain.workout.domain.template;

import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.exercise.domain.exercise.ExerciseType;
import com.roufit.backend.domain.workout.dto.request.SetTemplateRequest;
import com.roufit.backend.domain.workout.dto.response.SetTemplateResponse;
import com.roufit.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "set_template")
public class SetTemplate extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "set_template_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_template_id")
    private WorkoutTemplate workoutTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private Integer setCount;

    private Integer restPeriod;

    private Integer goalRepetition;

    private Integer goalTime;

    private Integer increaseOffset;

    private Integer additionalWeight;

    public void increaseIntensity() {
        if (exercise.getType().equals(ExerciseType.COUNT)) {
            goalRepetition += increaseOffset;
            return;
        }
        goalTime += increaseOffset;
    }

    @Builder
    public SetTemplate(WorkoutTemplate workoutTemplate, Exercise exercise,
                       SetTemplateRequest request) {
        this.workoutTemplate = workoutTemplate;
        this.exercise = exercise;
        this.setCount = request.getSetCount();
        this.restPeriod = request.getRestPeriod();
        this.goalRepetition = request.getGoalRepetition();
        this.goalTime = request.getGoalTime();
        this.increaseOffset = request.getIncreaseOffset();
        this.additionalWeight = request.getAdditionalWeight();
    }

    public SetTemplateResponse toDto() {
        return SetTemplateResponse.builder()
                .id(id)
                .setCount(setCount)
                .restPeriod(restPeriod)
                .goalRepetition(goalRepetition)
                .goalTime(goalTime)
                .additionalWeight(additionalWeight)
                .build();
    }
}
