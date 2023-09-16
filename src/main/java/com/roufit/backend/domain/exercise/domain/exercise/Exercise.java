package com.roufit.backend.domain.exercise.domain.exercise;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    @Builder
    public Exercise(String name, String description,
                    Equipment equipment, ExerciseType type) {
        this.name = name;
        this.description = description;
        this.equipment = equipment;
        this.type = type;
    }
}
