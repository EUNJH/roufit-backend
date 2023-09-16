package com.roufit.backend.domain.exercise.domain.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum CategoryLevel {

    CATEGORY3("소분류"),
    CATEGORY2("중분류"),
    CATEGORY1("대분류")
    ;

    public final String viewName;

    public static CategoryLevel nextLevel(CategoryLevel parent) {
        switch (parent) {
            case CATEGORY1 -> {
                return CATEGORY2;
            }
            case CATEGORY2 -> {
                return CATEGORY3;
            }
        }
        throw new NoSuchElementException(); //TODO 이미 최하위 카테고리
    }
}
