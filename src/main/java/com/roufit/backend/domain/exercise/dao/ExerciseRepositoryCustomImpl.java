package com.roufit.backend.domain.exercise.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.roufit.backend.domain.exercise.domain.exercise.QExercise.exercise;
import static com.roufit.backend.domain.exercise.domain.exercise.QExerciseCategory.exerciseCategory;

@RequiredArgsConstructor
@Repository
public class ExerciseRepositoryCustomImpl implements ExerciseRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public List<Exercise> findByCategory(Long categoryId) {
        return jpaQueryFactory.selectFrom(exercise)
                .join(exerciseCategory)
                .on(exercise.id.eq(exerciseCategory.id))
                .where(exerciseCategory.id.eq(categoryId))
                .fetch();
    }
}
