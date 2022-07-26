package com.pismo.api.exceptionhandler;

import com.pismo.core.exceptions.BusinessException;
import com.pismo.core.exceptions.ErrorDetail;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorDetail> handleBusinessException(final BusinessException businessException) {
        final ErrorDetail errorDetail = new ErrorDetail(List.of(businessException.getMessage()));
        return ResponseEntity.status(businessException.getHttpStatus()).body(errorDetail);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorDetail> handleConstraintValidationException(final ConstraintViolationException constraintViolationException) {
        final List<String> errors = constraintViolationException.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ErrorDetail(errors));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> handleConstraintValidationException(final MethodArgumentNotValidException methodArgumentNotValidException) {
        final List<String> errors = methodArgumentNotValidException.getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ErrorDetail(errors));
    }


}
