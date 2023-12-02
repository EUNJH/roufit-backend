package com.roufit.backend.domain.workout.dao.record;

import com.roufit.backend.domain.workout.domain.record.WorkoutRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRecordRepository extends JpaRepository<WorkoutRecord, Long>, WorkoutRecordRepositoryCustom {
}
