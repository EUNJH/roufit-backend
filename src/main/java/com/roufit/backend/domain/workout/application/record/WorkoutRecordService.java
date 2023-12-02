package com.roufit.backend.domain.workout.application.record;

import com.roufit.backend.domain.habit.application.HabitTrackService;
import com.roufit.backend.domain.user.application.UserService;
import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.domain.workout.application.template.WorkoutTemplateService;
import com.roufit.backend.domain.workout.dao.record.WorkoutRecordRepository;
import com.roufit.backend.domain.workout.domain.record.WorkoutRecord;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import com.roufit.backend.domain.workout.dto.response.WorkoutRecordResponse;
import com.roufit.backend.domain.workout.dto.request.WorkoutRecordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WorkoutRecordService {

    private final WorkoutRecordRepository workoutRecordRepository;
    private final WorkoutTemplateService workoutTemplateService;
    private final SetRecordService setRecordService;
    private final UserService userService;
    private final HabitTrackService habitTrackService;

    @Transactional
    public void create(WorkoutRecordRequest request, Long userId) {
        WorkoutTemplate template = workoutTemplateService
                .findById(request.getWorkoutId());
        User user = userService.getReferenceById(userId);
        WorkoutRecord workoutRecord = WorkoutRecord.builder()
                .user(user)
                .workoutTemplate(template)
                .request(request)
                .build();
        template.updatePerformDate(workoutRecord.getStartTime());
        workoutRecordRepository.save(workoutRecord);
        setRecordService.createAll(workoutRecord, request, template);
        habitTrackService.createAfterWorkout(workoutRecord, user);
    }

    public List<WorkoutRecordResponse> findByUserIdWithPagination(Long lastId, Long userId) {
        List<WorkoutRecord> workoutRecords =
                workoutRecordRepository.findAllByUserId(lastId, userId);
        return workoutRecords.stream()
                .map(w -> WorkoutRecordResponse.builder()
                        .workoutRecord(w)
                        .build())
                .toList();
    }

}
