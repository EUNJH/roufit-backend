package com.roufit.backend.domain.workout.application;


import com.roufit.backend.domain.exercise.application.ExerciseService;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.workout.dao.SetTemplateRepository;
import com.roufit.backend.domain.workout.domain.SetTemplate;
import com.roufit.backend.domain.workout.domain.WorkoutTemplate;
import com.roufit.backend.domain.workout.dto.request.SetTemplateRequest;
import com.roufit.backend.global.error.BusinessException;
import com.roufit.backend.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class SetTemplateService {

    private final SetTemplateRepository setTemplateRepository;
    private final ExerciseService exerciseService;

    @Transactional
    public void createSetTemplates(List<SetTemplateRequest> requests,
                                             WorkoutTemplate workoutTemplate) {
        List<Long> exerciseIds = requests.stream()
                .map(SetTemplateRequest::getExerciseId)
                .toList();
        List<Exercise> exercises = exerciseService.findByIds(exerciseIds);

        if(exercises.isEmpty()) {
            throw new NoSuchElementException("해당 운동이 없습니다.");
        }

        List<SetTemplate> setTemplates = requests.stream()
                .map(request ->
                    request.toEntity(workoutTemplate,
                            extractExercise(request, exercises)
                            )
                )
                .toList();

        setTemplateRepository.saveAll(setTemplates);
    }

    private Exercise extractExercise(SetTemplateRequest request, List<Exercise> exercises) {
        return exercises.stream()
                .filter(exercise -> exercise.getId().equals(request.getExerciseId()))
                .findAny().orElseThrow(NoSuchElementException::new);
    }

    public SetTemplate findById(Long id) {
        return setTemplateRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_LAST_SET_TEMPLATE));
    }
}
