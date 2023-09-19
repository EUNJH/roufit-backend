package com.roufit.backend.domain.workout.application;

import com.roufit.backend.domain.user.application.UserService;
import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.domain.workout.dao.SetRecordRepository;
import com.roufit.backend.domain.workout.dao.WorkoutRecordRepository;
import com.roufit.backend.domain.workout.domain.SetRecord;
import com.roufit.backend.domain.workout.domain.WorkoutRecord;
import com.roufit.backend.domain.workout.domain.SetTemplate;
import com.roufit.backend.domain.workout.dto.request.SetRecordRequest;
import com.roufit.backend.domain.workout.dto.request.WorkoutRecordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WorkoutRecordService {

    private final WorkoutRecordRepository workoutRecordRepository;
    private final WorkoutTemplateService workoutTemplateService;
    private final SetRecordService setRecordService;
    private final UserService userService;

    @Transactional
    public void createWorkoutRecord(WorkoutRecordRequest request,
                                  SecurityUserDto userDto) {

        WorkoutRecord workoutRecord = request.getWorkoutRecord(
                userService.findByEmail(userDto.getEmail()),
                workoutTemplateService.findById(request.getWorkoutId())
        );
        workoutRecordRepository.save(workoutRecord);
        setRecordService.createAll(workoutRecord, request.getSetRecordRequests());
    }

}
