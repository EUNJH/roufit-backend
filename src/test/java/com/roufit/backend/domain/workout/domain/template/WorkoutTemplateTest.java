package com.roufit.backend.domain.workout.domain.template;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WorkoutTemplateTest {

    @Test
    public void 수행날짜_업데이트() throws Exception {
        //given
        InitTemplate initTemplate = new InitTemplate();
        LocalDateTime startTime = LocalDateTime.of(2023, 10, 11, 23, 34);
        //when
        initTemplate.workoutTemplate.updatePerformDate(startTime);
        //then
        assertThat(initTemplate.workoutTemplate.getRecentPerformDate())
                .isEqualTo(LocalDate.of(2023, 10, 11));
    }
}