package com.roufit.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND_LAST_WORKOUT_TEMPLATE(404, "GLOBAL_404_1", "워크아웃 템플릿을 찾을 수 없습니다."),
    NOT_FOUND_LAST_SET_TEMPLATE(404, "GLOBAL_404_2", "세트 템플릿을 찾을 수 없습니다.");

    private final int status;
    private final String code;
    private final String reason;
}
