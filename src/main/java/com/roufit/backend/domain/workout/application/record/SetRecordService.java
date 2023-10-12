package com.roufit.backend.domain.workout.application.record;

import com.roufit.backend.domain.workout.dao.record.SetRecordRepository;
import com.roufit.backend.domain.workout.domain.record.SetRecord;
import com.roufit.backend.domain.workout.domain.template.SetTemplate;
import com.roufit.backend.domain.workout.domain.record.WorkoutRecord;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import com.roufit.backend.domain.workout.dto.request.WorkoutRecordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SetRecordService {

    private final SetRecordRepository setRecordRepository;

    @Transactional
    public void createAll(WorkoutRecord workoutRecord,
                          WorkoutRecordRequest request,
                          WorkoutTemplate template) {
        List<SetTemplate> setTemplates = template.getSetTemplates();
        List<SetRecord> setRecords = new ArrayList<>();

        for(SetTemplate setTemplate:setTemplates) {
            Optional<SetRecord> setRecord = request.getSetRecordRequests()
                    .stream()
                    .filter(r -> r.getSetTemplateId().equals(setTemplate.getId()))
                    .map(r -> SetRecord.builder()
                            .workoutRecord(workoutRecord)
                            .setTemplate(setTemplate)
                            .request(r)
                            .build())
                    .findFirst();
            setRecord.ifPresent(record -> {
                record.checkIncreasingPerformance();
                setRecords.add(record);
            });
        }

        setRecordRepository.saveAll(setRecords);
    }

}
