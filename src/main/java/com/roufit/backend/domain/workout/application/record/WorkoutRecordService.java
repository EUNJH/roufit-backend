package com.roufit.backend.domain.workout.application.record;

import com.roufit.backend.domain.habit.application.HabitTrackService;
import com.roufit.backend.domain.user.application.UserService;
import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.domain.workout.application.template.WorkoutTemplateService;
import com.roufit.backend.domain.workout.dao.record.WorkoutRecordRepository;
import com.roufit.backend.domain.workout.domain.record.WorkoutRecord;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import com.roufit.backend.domain.workout.dto.request.WorkoutRecordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class WorkoutRecordService {

    private final WorkoutRecordRepository workoutRecordRepository;
    private final WorkoutTemplateService workoutTemplateService;
    private final SetRecordService setRecordService;
    private final UserService userService;
    private final HabitTrackService habitTrackService;

    @Transactional
    public void create(WorkoutRecordRequest request,
                                  SecurityUserDto userDto) {
        WorkoutTemplate template = workoutTemplateService.findById(request.getWorkoutId());
        User user = userService.getReferenceById(userDto.getId());
        WorkoutRecord workoutRecord = WorkoutRecord.builder()
                .user(user)
                .workoutTemplate(template)
                .request(request)
                .build();
        template.updatePerformDate();
        workoutRecordRepository.save(workoutRecord);
        setRecordService.createAll(workoutRecord, request);
        habitTrackService.createAfterWorkout(workoutRecord, user);
    }

}
