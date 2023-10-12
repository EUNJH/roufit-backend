package com.roufit.backend.domain.habit.dto;

import com.roufit.backend.domain.habit.domain.HabitTrack;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HabitResponse {

    private LocalDate recordDate;
    private Boolean isCompleteWorkout;
    private Integer consecutiveDays;

    public HabitResponse(HabitTrack habitTrack) {
        this.recordDate = habitTrack.getRecordDate();
        this.isCompleteWorkout = habitTrack.getIsCompleteWorkout();
        this.consecutiveDays = habitTrack.getConsecutiveDays();
    }
}
