package com.roufit.backend.domain.workout.domain.template;

import com.roufit.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "workout_template")
public class WorkoutTemplate extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "workout_template_id")
    private Long id;

    private String routineName;

    private String recentPerformDate;
}
