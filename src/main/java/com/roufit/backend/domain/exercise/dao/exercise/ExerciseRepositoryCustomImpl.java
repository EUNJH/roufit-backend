package com.roufit.backend.domain.exercise.dao.exercise;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.global.common.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.roufit.backend.domain.exercise.domain.exercise.QExercise.exercise;
import static com.roufit.backend.domain.exercise.domain.exercise.QExerciseCategory.exerciseCategory;

@RequiredArgsConstructor
@Repository
public class ExerciseRepositoryCustomImpl implements ExerciseRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public List<Exercise> findByCategoryAndStatus(Long categoryId) {
        return jpaQueryFactory.selectFrom(exercise)
                .join(exerciseCategory).on(exercise.eq(exerciseCategory.exercise))
                .where(exerciseCategory.category.id.eq(categoryId))
                .where(exercise.status.eq(Status.ACTIVE))
                .fetch();
    }
}
