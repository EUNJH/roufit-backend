package com.roufit.backend.global.error.exception;

public class DuplicateException extends BusinessException{
    public DuplicateException(String value) {
        super(value, ErrorCode.VALUE_DUPLICATION);
    }

    public DuplicateException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
