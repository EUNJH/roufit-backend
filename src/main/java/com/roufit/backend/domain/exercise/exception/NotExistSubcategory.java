package com.roufit.backend.domain.exercise.exception;

import com.roufit.backend.global.error.exception.BusinessException;
import com.roufit.backend.global.error.exception.ErrorCode;

public class NotExistSubcategory extends BusinessException {
    public NotExistSubcategory(ErrorCode errorCode) {
        super(errorCode);
    }
}
