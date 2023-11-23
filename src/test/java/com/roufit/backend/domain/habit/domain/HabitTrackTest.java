package com.roufit.backend.domain.habit.domain;

import com.roufit.backend.domain.user.domain.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HabitTrackTest {

    @Test
    public void 최근3일이내_운기록_없을때() throws Exception {
        //given
        LocalDate date = LocalDate.of(2023, 9, 8);
        HabitTrack todayTrack = HabitTrack.builder()
                .recordDate(date)
                .isCompleteWorkout(true)
                .user(new User())
                .build();
        //when
        todayTrack.updateConsecutiveDays(null);
        //then
        assertThat(todayTrack.getConsecutiveDays()).isEqualTo(1);
        assertThat(todayTrack.getConsecutiveStartDate()).isEqualTo(date);
    }

    @Test
    public void 최근3일이내_운기록_있을때() throws Exception {
        //given
        LocalDate date = LocalDate.of(2023, 9, 8);
        LocalDate lastDate1 = LocalDate.of(2023, 9, 6);
        LocalDate lastDate2 = LocalDate.of(2023, 9, 4);
        LocalDate lastDate3 = LocalDate.of(2023, 9, 2);

        HabitTrack todayTrack = HabitTrack.builder()
                .recordDate(date)
                .isCompleteWorkout(true)
                .build();
        HabitTrack lastTrack1 = HabitTrack.builder()
                .recordDate(lastDate1)
                .isCompleteWorkout(true)
                .build();
        HabitTrack lastTrack2 = HabitTrack.builder()
                .recordDate(lastDate2)
                .isCompleteWorkout(true)
                .build();
        HabitTrack lastTrack3 = HabitTrack.builder()
                .recordDate(lastDate3)
                .isCompleteWorkout(true)
                .build();
        //when
        lastTrack3.updateConsecutiveDays(null);
        lastTrack2.updateConsecutiveDays(List.of(lastTrack3));
        lastTrack1.updateConsecutiveDays(List.of(lastTrack3, lastTrack2));
        todayTrack.updateConsecutiveDays(List.of(lastTrack1, lastTrack2, lastTrack3));
        //then
        assertThat(todayTrack.getConsecutiveDays()).isEqualTo(7);
        assertThat(todayTrack.getRecordDate()).isEqualTo(date);
        assertThat(todayTrack.getConsecutiveStartDate()).isEqualTo(lastDate3);
    }

}