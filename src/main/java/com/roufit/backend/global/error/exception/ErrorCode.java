package com.roufit.backend.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * Common
     */
    ENTITY_NOT_FOUND(400, "C001", "Entity Not Found"),
    INVALID_INPUT_VALUE(400, "C002", "Invalid Input Value"),
    INTERNAL_SERVER_ERROR(500, "C003", "Internal Server Error"),
    VALUE_DUPLICATION(400, "C004", "Value Duplication"),

    /**
     * Exercise Error
     */
    EXERCISE_NAME_DUPLICATION(400,"E001", "Exercise Name Duplication"),
    EXERCISE_ID_NOT_FOUND(400,"E002", "Exercise Id Not Found"),
    CATEGORY_PARENT_NOT_FOUND(400, "E003", "Category Parent Not Found"),
    CATEGORY_ID_NOT_FOUND(400, "E004", "Category Id Entity Not Found"),

    /**
     * Template Error
     */
    NOT_FOUND_LAST_WORKOUT_TEMPLATE(404, "GLOBAL_404_1", "워크아웃 템플릿을 찾을 수 없습니다."),
    NOT_FOUND_LAST_SET_TEMPLATE(404, "GLOBAL_404_2", "세트 템플릿을 찾을 수 없습니다.");

    private final int status;
    private final String code;
    private final String message;
}
