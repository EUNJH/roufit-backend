package com.roufit.backend.global.error.exception;

public class InvalidRequestException  extends BusinessException {
    public InvalidRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
