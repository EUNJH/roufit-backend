package com.roufit.backend.domain.workout.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Equipment {
    NONE("없음"),
    BABEL("바벨"),
    DUMBBELL("덤벨"),
    BAND("밴드"),
    PULL_UP_BAR("풀업 바");

    private String equipmentName;
}
