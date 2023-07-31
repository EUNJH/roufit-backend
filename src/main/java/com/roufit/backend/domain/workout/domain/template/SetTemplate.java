package com.roufit.backend.domain.workout.domain.template;

import com.roufit.backend.domain.workout.domain.Exercise;
import com.roufit.backend.domain.workout.domain.ExerciseType;
import com.roufit.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

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

    @Enumerated(value = EnumType.STRING)
    private ExerciseType type;

    private int additionalWeight;
}
