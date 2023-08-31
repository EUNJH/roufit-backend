package com.roufit.backend.domain.workout.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryLevel {

    CATEGORY1("대분류"),
    CATEGORY2("중분류"),
    CATEGORY3("소분류"),
    CATEGORY4("세분류");

    public final String viewName;
}
