package com.roufit.backend.domain.workout.application.record;

import com.roufit.backend.domain.workout.application.template.SetTemplateService;
import com.roufit.backend.domain.workout.dao.record.SetRecordRepository;
import com.roufit.backend.domain.workout.domain.record.SetRecord;
import com.roufit.backend.domain.workout.domain.template.SetTemplate;
import com.roufit.backend.domain.workout.domain.record.WorkoutRecord;
import com.roufit.backend.domain.workout.dto.request.SetRecordRequest;
import com.roufit.backend.domain.workout.dto.request.WorkoutRecordRequest;
import com.roufit.backend.global.error.exception.ErrorCode;
import com.roufit.backend.global.error.exception.InvalidValueException;
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
    private final SetTemplateService setTemplateService;

    @Transactional
    public void createAll(WorkoutRecord workoutRecord, WorkoutRecordRequest request) {
        List<SetRecord> setRecords = new ArrayList<>();
        List<SetTemplate> setTemplates =
                setTemplateService.findAllByIds(request.getAllSetTemplateIds());

        for(SetTemplate setTemplate:setTemplates) {
            Optional<SetRecord> setRecord = request.getSetRecordRequests()
                    .stream()
                    .filter(r -> r.getSetTemplateId().equals(setTemplate.getId()))
                    .map(r -> SetRecord.builder()
                            .workoutRecord(workoutRecord)
                            .setTemplate(setTemplate)
                            .build())
                    .findFirst();
            setRecord.ifPresent(setRecords::add);
        }

        setRecordRepository.saveAll(setRecords);
    }

}
