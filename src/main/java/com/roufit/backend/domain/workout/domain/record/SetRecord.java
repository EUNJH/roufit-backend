package com.roufit.backend.domain.workout.domain.record;

import com.roufit.backend.domain.workout.domain.Exercise;
import com.roufit.backend.domain.workout.domain.ExerciseType;
import com.roufit.backend.domain.workout.domain.template.SetTemplate;
import com.roufit.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "set")
public class SetRecord extends BaseEntity {

    @Id @GeneratedValue
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

    private int setCount;

    private int restPeriod;

    private int goalRepetition;

    private int goalTime;

    @Enumerated(value = EnumType.STRING)
    private ExerciseType type;

    private int additionalWeight;

    private String successSet;

    private boolean isCompleted;

    private boolean isSuccess;

    @Builder
    public SetRecord(WorkoutRecord workoutRecord, Exercise exercise, SetTemplate setTemplate,
                     String successSet, boolean isCompleted, boolean isSuccess) {
        this.workoutRecord = workoutRecord;
        this.exercise = exercise;
        setSetTemplateData(setTemplate);
        this.successSet = successSet;
        this.isSuccess = checkSuccess(successSet);
        this.isCompleted = isCompleted;
    }

    private void setSetTemplateData(SetTemplate setTemplate) {
        this.setTemplate = setTemplate;
        this.setCount = setTemplate.getSetCount();
        this.restPeriod = setTemplate.getRestPeriod();
        this.goalRepetition = setTemplate.getGoalRepetition();
        this.goalTime = setTemplate.getGoalTime();
        this.type = setTemplate.getType();
        this.additionalWeight = setTemplate.getAdditionalWeight();
    }

    public boolean checkSuccess(String successSet) {
        return Arrays.stream(successSet.split("\\."))
                .allMatch(s -> s.equals("1"));
    }

    public void checkIncreasingPerformance() {
        if(isSuccess) {
            setTemplate.increasingPerformance();
        }
    }


}
