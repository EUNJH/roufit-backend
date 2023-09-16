package com.roufit.backend.domain.workout.application;

import com.roufit.backend.domain.workout.dao.SetRecordRepository;
import com.roufit.backend.domain.workout.domain.SetRecord;
import com.roufit.backend.domain.workout.domain.SetTemplate;
import com.roufit.backend.domain.workout.domain.WorkoutRecord;
import com.roufit.backend.domain.workout.dto.request.SetRecordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SetRecordService {

    private final SetRecordRepository setRecordRepository;
    private final SetTemplateService setTemplateService;

    @Transactional
    public void createAll(WorkoutRecord workoutRecord, List<SetRecordRequest> requests) {
        List<SetRecord> setRecords = new ArrayList<>();

        for(SetRecordRequest request:requests) {
            setRecords.add(
                    getSetRecord(workoutRecord, request)
            );
        }
        setRecordRepository.saveAll(setRecords);
    }

    public SetRecord getSetRecord(WorkoutRecord workoutRecord, SetRecordRequest request) {
        SetTemplate setTemplate = setTemplateService.findById(request.getSetTemplateId());
        return request.mapToEntity(workoutRecord, setTemplate);
    }
}
