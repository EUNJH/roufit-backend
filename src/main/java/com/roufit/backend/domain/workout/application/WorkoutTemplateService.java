package com.roufit.backend.domain.workout.application;

import com.roufit.backend.domain.workout.dao.SetTemplateRepository;
import com.roufit.backend.domain.workout.dao.WorkoutTemplateRepository;
import com.roufit.backend.domain.workout.domain.template.SetTemplate;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import com.roufit.backend.global.error.BusinessException;
import com.roufit.backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class WorkoutTemplateService {

    private final WorkoutTemplateRepository workoutTemplateRepository;
    private final SetTemplateRepository setTemplateRepository;

    public WorkoutTemplate findWorkoutTemplateById(Long id) {
        return workoutTemplateRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_LAST_WORKOUT_TEMPLATE));
    }

    public SetTemplate findSetTemplateById(Long id) {
        return setTemplateRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_LAST_SET_TEMPLATE));
    }
}
