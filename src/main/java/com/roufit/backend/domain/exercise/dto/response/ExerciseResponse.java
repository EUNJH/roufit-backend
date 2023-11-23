package com.roufit.backend.domain.exercise.dto.response;

import com.roufit.backend.domain.exercise.domain.category.Category;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.exercise.domain.exercise.ExerciseCategory;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ExerciseResponse {

    private String name;
    private String description;
    private String equipment;
    private String type;
    Map<String, String> categories = new HashMap<>();

    @Builder
    public ExerciseResponse(String name, String description,
                            String equipment, String type,
                            Map<String, String> categories) {
        this.name = name;
        this.description = description;
        this.equipment = equipment;
        this.type = type;
        this.categories = categories;
    }

    public static ExerciseResponse toDto(List<ExerciseCategory> exerciseCategory) {
        Exercise exercise = exerciseCategory.get(0).getExercise();
        return ExerciseResponse.builder()
                .name(exercise.getName())
                .description(exercise.getDescription())
                .equipment(exercise.getEquipment().getEquipmentName())
                .type(exercise.getType().toString())
                .categories(exerciseCategory.stream()
                        .map(ExerciseCategory::getCategory)
                        .collect(Collectors.toMap(c -> c.getLevel().getViewName(), Category::getTitle)))
                .build();

    }
}
