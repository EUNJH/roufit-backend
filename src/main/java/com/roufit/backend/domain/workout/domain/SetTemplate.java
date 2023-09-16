package com.roufit.backend.domain.workout.domain;

import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.exercise.domain.exercise.ExerciseType;
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

    private int setCount;

    private int restPeriod;

    private int goalRepetition;

    private int goalTime;

    private int increaseOffset;

    private int additionalWeight;

    public void increasingPerformance() {
        if (exercise.getType().equals(ExerciseType.COUNT)) {
            goalRepetition += increaseOffset;
            return;
        }
        goalTime += increaseOffset;
    }

    @Builder
    public SetTemplate(WorkoutTemplate workoutTemplate, Exercise exercise,
                       int setCount, int restPeriod,
                       int goalRepetition, int goalTime,
                       int increaseOffset, int additionalWeight) {
        this.workoutTemplate = workoutTemplate;
        this.exercise = exercise;
        this.setCount = setCount;
        this.restPeriod = restPeriod;
        this.goalRepetition = goalRepetition;
        this.goalTime = goalTime;
        this.increaseOffset = increaseOffset;
        this.additionalWeight = additionalWeight;
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
