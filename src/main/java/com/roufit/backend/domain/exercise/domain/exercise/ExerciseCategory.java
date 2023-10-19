package com.roufit.backend.domain.exercise.domain.exercise;

import com.roufit.backend.domain.exercise.domain.category.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "exercise_category")
public class ExerciseCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    @Builder
    public ExerciseCategory(Exercise exercise, Category category) {
        this.exercise = exercise;
        this.category = category;
    }
}
