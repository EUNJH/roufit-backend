package com.roufit.backend.domain.exercise.domain.exercise;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "exercise")
public class Exercise {

    @Id @GeneratedValue
    @Column(name = "exercise_id")
    private Long id;

    private String name;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private Equipment equipment;

    @Enumerated(value = EnumType.STRING)
    private ExerciseType type;
}
