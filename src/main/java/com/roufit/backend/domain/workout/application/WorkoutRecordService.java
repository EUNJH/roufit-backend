package com.roufit.backend.domain.workout.application;

import com.roufit.backend.domain.member.domain.Member;
import com.roufit.backend.domain.workout.dao.SetRecordRepository;
import com.roufit.backend.domain.workout.dao.WorkoutRecordRepository;
import com.roufit.backend.domain.workout.domain.record.SetRecord;
import com.roufit.backend.domain.workout.domain.record.WorkoutRecord;
import com.roufit.backend.domain.workout.domain.template.SetTemplate;
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

    private final WorkoutTemplateService workoutTemplateService;
    private final WorkoutRecordRepository workoutRecordRepository;
    private final SetRecordRepository setRecordRepository;

    @Transactional
    public void saveWorkoutRecord(WorkoutRecordRequest request, Member member) {

        WorkoutRecord workoutRecord = request.getWorkoutRecord(
                member, workoutTemplateService.findWorkoutTemplateById(request.getWorkoutId())
        );
        workoutRecordRepository.save(workoutRecord);
        saveSetRecords(workoutRecord, request.getSetRecordRequests());
    }

    @Transactional
    public void saveSetRecords(WorkoutRecord workoutRecord, List<SetRecordRequest> requests) {
        List<SetRecord> setRecords = new ArrayList<>();

        for(SetRecordRequest request:requests) {
            setRecords.add(
                    getSetRecord(workoutRecord, request)
            );
        }
        setRecordRepository.saveAll(setRecords);
    }

    public SetRecord getSetRecord(WorkoutRecord workoutRecord, SetRecordRequest request) {
        SetTemplate setTemplate = workoutTemplateService.findSetTemplateById(request.getSetTemplateId());
        return request.mapToEntity(workoutRecord, setTemplate);
    }


}
