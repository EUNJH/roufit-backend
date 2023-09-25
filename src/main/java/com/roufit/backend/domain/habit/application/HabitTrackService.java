package com.roufit.backend.domain.habit.application;

import com.roufit.backend.domain.habit.dao.HabitTrackRepository;
import com.roufit.backend.domain.habit.domain.HabitTrack;
import com.roufit.backend.domain.habit.dto.HabitResponse;
import com.roufit.backend.domain.user.application.UserService;
import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.user.dto.SecurityUserDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@Service
public class HabitTrackService {

    private final HabitTrackRepository habitTrackRepository;
    private final UserService userService;

    @Transactional
    public void createAfterWorkout(boolean isComplete, User user) {
        List<HabitTrack> lastThreeDays = habitTrackRepository.findLastThreeDays(user);
        HabitTrack habitTrack = HabitTrack.builder()
                .isCompleteWorkout(isComplete)
                .lastThreeDays(lastThreeDays)
                .user(user)
                .build();
        habitTrackRepository.save(habitTrack);
    }

    public List<HabitResponse> getByDate(Integer year, Integer month, SecurityUserDto userDto) {
        User user = userService.getReferenceById(userDto.getId());
        List<HabitTrack> habitTracks = habitTrackRepository.findByLocalDate(year, month, user);
        return habitTracks.stream()
                .map(HabitResponse::new)
                .toList();
    }

}
