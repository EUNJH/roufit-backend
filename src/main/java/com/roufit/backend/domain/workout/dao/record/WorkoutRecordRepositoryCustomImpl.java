package com.roufit.backend.domain.workout.dao.record;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.roufit.backend.domain.workout.domain.record.WorkoutRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.roufit.backend.domain.workout.domain.record.QWorkoutRecord.workoutRecord;

@RequiredArgsConstructor
@Repository
public class WorkoutRecordRepositoryCustomImpl implements WorkoutRecordRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    public List<WorkoutRecord> findAllByUserId(Long lastId, Long userId) {
        return jpaQueryFactory.selectFrom(workoutRecord)
                .distinct()
                .where(workoutRecord.user.id.eq(userId), ltRecordId(lastId))
                .limit(7)
                .fetch();
    }

    private BooleanExpression ltRecordId(Long lastId) {
        if(lastId == null) {
            return null;
        }
        return workoutRecord.id.lt(lastId);
    }
}
