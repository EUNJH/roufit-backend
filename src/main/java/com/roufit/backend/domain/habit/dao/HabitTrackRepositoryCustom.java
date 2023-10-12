package com.roufit.backend.domain.habit.dao;

import com.roufit.backend.domain.habit.domain.HabitTrack;
import com.roufit.backend.domain.user.domain.User;

import java.util.List;

public interface HabitTrackRepositoryCustom {
    List<HabitTrack> findLastThreeDays(User user);
    List<HabitTrack> findByLocalDate(int year, int month, User user);
}
