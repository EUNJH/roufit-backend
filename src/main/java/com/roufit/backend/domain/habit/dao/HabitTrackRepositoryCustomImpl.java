package com.roufit.backend.domain.habit.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.roufit.backend.domain.habit.domain.HabitTrack;
import com.roufit.backend.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.roufit.backend.domain.habit.domain.QHabitTrack.habitTrack;


@RequiredArgsConstructor
@Repository
public class HabitTrackRepositoryCustomImpl implements HabitTrackRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<HabitTrack> findLastThreeDays(User user) {
        LocalDate today = LocalDate.now();
        LocalDate twoDaysAgo = today.minusDays(3);
        return jpaQueryFactory.selectFrom(habitTrack)
                .where(habitTrack.user.eq(user))
                .where(habitTrack.recordDate.between(twoDaysAgo, today))
                .fetch();
    }

    public List<HabitTrack> findByLocalDate(int year, int month, User user) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        return jpaQueryFactory.selectFrom(habitTrack)
                .where(habitTrack.recordDate.between(startDate, endDate))
                .fetch();
    }
}
