package com.roufit.backend.domain.workout.domain.template;

import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.exercise.domain.exercise.ExerciseType;
import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.workout.dto.request.SetTemplateRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InitTemplate {

    public WorkoutTemplate workoutTemplate;
    public List<SetTemplate> setTemplates = new ArrayList<>();

    public InitTemplate() {
        setTemplates.add(SetTemplate.builder()
                        .exercise(new Exercise())
                        .workoutTemplate(workoutTemplate)
                        .exercise(Exercise.builder().type(ExerciseType.COUNT).build())
                        .request(SetTemplateRequest.builder()
                                .setCount(5)
                                .exerciseId(1L)
                                .goalRepetition(10)
                                .restPeriod(90)
                                .increaseOffset(3)
                                .setCount(15)
                                .build())
                .build());
        setTemplates.add(SetTemplate.builder()
                .exercise(new Exercise())
                .workoutTemplate(workoutTemplate)
                .exercise(Exercise.builder().type(ExerciseType.DURATION).build())
                .request(SetTemplateRequest.builder()
                        .setCount(5)
                        .exerciseId(2L)
                        .goalTime(60)
                        .restPeriod(90)
                        .increaseOffset(5)
                        .setCount(15)
                        .build())
                .build());
        workoutTemplate = WorkoutTemplate.builder()
                .templateName("test")
                .user(new User())
                .build();
        workoutTemplate.addSet(setTemplates);
    }
}
