package com.roufit.backend.domain.workout.dao.template;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import com.roufit.backend.global.error.exception.DuplicateException;
import com.roufit.backend.global.error.exception.EntityNotFoundException;
import com.roufit.backend.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.roufit.backend.domain.exercise.domain.exercise.QExercise.exercise;
import static com.roufit.backend.domain.workout.domain.template.QSetTemplate.setTemplate;
import static com.roufit.backend.domain.workout.domain.template.QWorkoutTemplate.workoutTemplate;


@RequiredArgsConstructor
@Repository
public class WorkoutTemplateRepositoryCustomImpl implements WorkoutTemplateRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public WorkoutTemplate findTemplateAndSetById(Long id) {
        List<WorkoutTemplate> result = jpaQueryFactory.selectFrom(workoutTemplate)
                .join(workoutTemplate.setTemplates)
                .fetchJoin()
                .where(workoutTemplate.id.eq(id))
                .fetch();
        if(result.isEmpty()) {
            throw new EntityNotFoundException(
                    String.valueOf(id),
                    ErrorCode.WORKOUT_TEMPLATE_NOT_FOUND
            );
        }

        return result.get(0);
    }
}
