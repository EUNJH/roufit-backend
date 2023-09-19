package com.roufit.backend.domain.workout.dao;

import com.roufit.backend.domain.workout.domain.SetRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetRecordRepository extends JpaRepository<SetRecord, Long> {
}
