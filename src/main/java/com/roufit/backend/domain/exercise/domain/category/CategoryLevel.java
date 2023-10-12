package com.roufit.backend.domain.exercise.domain.category;

import com.roufit.backend.domain.exercise.exception.NotExistSubcategory;
import com.roufit.backend.global.error.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum CategoryLevel {

    CATEGORY3("소분류"),
    CATEGORY2("중분류"),
    CATEGORY1("대분류"),
    ROOT("루트")
    ;

    public final String viewName;

    public static CategoryLevel nextLevel(CategoryLevel parent) {
        switch (parent) {
            case ROOT -> {
                return CATEGORY1;
            }
            case CATEGORY1 -> {
                return CATEGORY2;
            }
            case CATEGORY2 -> {
                return CATEGORY3;
            }
        }
        throw new NotExistSubcategory(ErrorCode.NOT_EXIST_SUBCATEGORY);
    }
}
