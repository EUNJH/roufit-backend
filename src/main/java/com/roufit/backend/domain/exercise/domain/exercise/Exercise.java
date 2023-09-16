package com.roufit.backend.domain.exercise.domain.exercise;

import com.roufit.backend.domain.exercise.dto.response.ExerciseResponse;
import com.roufit.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "exercise")
public class Exercise extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "exercise_id")
    private Long id;

    @Column(unique = true)
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

    public ExerciseResponse toDTO() {
        return ExerciseResponse.builder()
                .name(name)
                .description(description)
                .equipment(equipment.getEquipmentName())
                .type(type.name())
                .build();
    }
}
