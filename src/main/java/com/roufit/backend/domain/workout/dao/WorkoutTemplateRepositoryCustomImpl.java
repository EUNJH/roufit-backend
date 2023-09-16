package com.roufit.backend.domain.workout.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.roufit.backend.domain.workout.domain.WorkoutTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.roufit.backend.domain.workout.domain.QSetTemplate.setTemplate;
import static com.roufit.backend.domain.workout.domain.QWorkoutTemplate.workoutTemplate;

@RequiredArgsConstructor
@Repository
public class WorkoutTemplateRepositoryCustomImpl implements WorkoutTemplateRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public List<WorkoutTemplate> searchAll(Long userId) {
        return jpaQueryFactory.selectFrom(workoutTemplate)
                .join(workoutTemplate.setTemplates).fetchJoin()
                .join(setTemplate.exercise).fetchJoin()
                .fetch();
    }
}
