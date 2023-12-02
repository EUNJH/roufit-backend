package com.roufit.backend.domain.workout.dao.record;

import com.roufit.backend.domain.workout.domain.record.WorkoutRecord;

import java.util.List;

public interface WorkoutRecordRepositoryCustom {
    List<WorkoutRecord> findAllByUserId(Long lastId, Long userId);
}
