package com.roufit.backend.domain.workout.application.template;


import com.roufit.backend.domain.exercise.application.ExerciseFindService;
import com.roufit.backend.domain.exercise.application.ExerciseService;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.workout.dao.template.SetTemplateRepository;
import com.roufit.backend.domain.workout.domain.template.SetTemplate;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import com.roufit.backend.domain.workout.dto.request.SetTemplateRequest;
import com.roufit.backend.global.error.exception.BusinessException;
import com.roufit.backend.global.error.exception.EntityNotFoundException;
import com.roufit.backend.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class SetTemplateService {

    private final SetTemplateRepository setTemplateRepository;
    private final ExerciseFindService exerciseFindService;

    @Transactional
    public void createSetTemplates(List<SetTemplateRequest> requests,
                                             WorkoutTemplate workoutTemplate) {

        List<SetTemplate> setTemplates = requests.stream()
                .map(request ->
                        SetTemplate.builder()
                                .workoutTemplate(workoutTemplate)
                                .request(request)
                                .exercise(exerciseFindService.getReferenceById(request.getExerciseId()))
                                .build()
                )
                .toList();

        setTemplateRepository.saveAll(setTemplates);
    }

    public SetTemplate findById(Long id) {
        return setTemplateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SET_TEMPLATE_NOT_FOUND));
    }

    public List<SetTemplate> findAllByIds(final List<Long> ids) {
        List<SetTemplate> setTemplates = setTemplateRepository.findByIdIn(ids);
        if(setTemplates.size() != ids.size()) {
            throw new EntityNotFoundException(ErrorCode.SET_TEMPLATE_NOT_FOUND);
        }
        return setTemplates;
    }
}
