package com.roufit.backend.domain.workout.domain.record;

import com.roufit.backend.domain.workout.domain.Exercise;
import com.roufit.backend.domain.workout.domain.ExerciseType;
import com.roufit.backend.domain.workout.domain.template.SetTemplate;
import com.roufit.backend.global.common.BaseEntity;
import jakarta.persistence.*;

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

    private String exerciseName;

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

}
