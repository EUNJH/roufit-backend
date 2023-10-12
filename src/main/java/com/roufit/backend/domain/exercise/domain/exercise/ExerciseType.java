package com.roufit.backend.domain.exercise.domain.exercise;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum ExerciseType {

    COUNT, DURATION;

    @JsonCreator
    public static ExerciseType from(String value) {
        return Stream.of(ExerciseType.values())
                .filter(type -> type.name().equals(value.toUpperCase()))
                .findFirst()
                .orElse(null);
    }

}
