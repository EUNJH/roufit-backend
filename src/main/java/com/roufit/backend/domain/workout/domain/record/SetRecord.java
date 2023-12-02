package com.roufit.backend.domain.workout.domain.record;

import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.workout.domain.template.SetTemplate;
import com.roufit.backend.domain.workout.dto.request.SetRecordRequest;
import com.roufit.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "set_record")
public class SetRecord extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_record_id")
    private WorkoutRecord workoutRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "set_template_id")
    private SetTemplate setTemplate;

    private String exerciseName;
    private Integer setCount;

    private Integer restPeriod;

    private Integer goalRepetition;

    private Integer goalTime;

    private Integer additionalWeight;

    private Boolean isCompleted;

    private Boolean isSuccess;

    @Builder
    public SetRecord(WorkoutRecord workoutRecord,
                     SetTemplate setTemplate,
                     SetRecordRequest request) {
        this.workoutRecord = workoutRecord;
        setSetTemplateData(setTemplate);
        this.isSuccess = request.getIsSuccess();
        this.isCompleted = request.getIsCompleted();
    }

    private void setSetTemplateData(SetTemplate setTemplate) {
        this.setTemplate = setTemplate;
        this.setCount = setTemplate.getSetCount();
        this.restPeriod = setTemplate.getRestPeriod();
        this.goalRepetition = setTemplate.getGoalRepetition();
        this.goalTime = setTemplate.getGoalTime();
        this.additionalWeight = setTemplate.getAdditionalWeight();
        this.exercise = setTemplate.getExercise();
        this.exerciseName = setTemplate.getExercise().getName();
    }

    public void checkIncreasingPerformance() {
        if(isSuccess) {
            setTemplate.increaseIntensity();
        }
    }
}
