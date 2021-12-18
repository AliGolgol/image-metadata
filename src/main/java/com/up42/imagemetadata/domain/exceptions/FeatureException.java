package com.up42.imagemetadata.domain.exceptions;

import lombok.Getter;

public class FeatureException extends RuntimeException {
    @Getter
    private final ErrorCode errorCode;

    @Getter
    private final Object details;

    public FeatureException(ErrorCode errorCode, Object details, String message) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }
}
