package com.roufit.backend.domain.habit.dao;

import com.roufit.backend.domain.habit.domain.HabitTrack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitTrackRepository extends JpaRepository<HabitTrack, Long>, HabitTrackRepositoryCustom {
}
