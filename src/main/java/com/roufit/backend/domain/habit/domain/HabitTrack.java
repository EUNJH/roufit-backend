package com.roufit.backend.domain.habit.domain;

import com.roufit.backend.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "habit_level")
public class HabitTrack {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habit_level_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate recordDate;

    @Column(nullable = false)
    private Boolean isCompleteWorkout;

    @Column(nullable = false)
    private Integer consecutiveDays;

    @Column(nullable = false)
    private LocalDate consecutiveStartDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public HabitTrack(LocalDate recordDate, Boolean isCompleteWorkout, User user) {
        this.recordDate = recordDate;
        this.isCompleteWorkout = isCompleteWorkout;
        this.user = user;
    }

    public void updateConsecutiveDays(List<HabitTrack> lastThreeDays) {
        if(lastThreeDays == null) {
            this.consecutiveDays = 1;
            this.consecutiveStartDate = this.recordDate;
            return;
        }
        HabitTrack recentHabit = getRecentHabit(lastThreeDays);
        this.consecutiveDays = calculateConsecutiveDays(recentHabit);
        this.consecutiveStartDate = recentHabit.getConsecutiveStartDate();
    }

    private int calculateConsecutiveDays(HabitTrack recentHabit) {
        return recentHabit.getConsecutiveDays() + calculateDays(recentHabit.getRecordDate(), this.recordDate);
    }

    private HabitTrack getRecentHabit(List<HabitTrack> habitTracks) {
        return habitTracks.stream()
                .max(Comparator.comparing(HabitTrack::getRecordDate))
                .orElse(null);
    }

    private int calculateDays(LocalDate from, LocalDate to) {
        return Period.between(from, to).getDays();
    }


}
