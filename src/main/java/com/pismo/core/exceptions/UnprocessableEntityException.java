package com.pismo.core.exceptions;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends BusinessException {

    public UnprocessableEntityException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
