package com.roufit.backend.domain.workout.dao.record;

import com.roufit.backend.domain.workout.domain.record.SetRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetRecordRepository extends JpaRepository<SetRecord, Long> {
}
