package com.berkanterdogan.springsecuritylab.controller.errorhandler;

import com.berkanterdogan.springsecuritylab.dto.UnexpectedErrorDto;
import com.berkanterdogan.springsecuritylab.dto.ValidationErrorDto;
import com.berkanterdogan.springsecuritylab.dto.ValidationErrorResponseDto;
import com.berkanterdogan.springsecuritylab.exception.RefreshTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@Slf4j
@ControllerAdvice
public class SpringSecurityLabErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ValidationErrorResponseDto validationErrorResponseDto = new ValidationErrorResponseDto();

        Map<String, Set<String>> errorFieldMessageMap = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            if (errorFieldMessageMap.containsKey(fieldName)) {
                errorFieldMessageMap.get(fieldName).add(errorMessage);
            } else {
                errorFieldMessageMap.put(fieldName, new HashSet<>(Collections.singletonList(errorMessage)));
            }
        }

        for (Map.Entry<String, Set<String>> errorFieldMessageEntry : errorFieldMessageMap.entrySet()) {
            validationErrorResponseDto.getValidationErrors().add(
                    ValidationErrorDto.builder()
                            .fieldName(errorFieldMessageEntry.getKey())
                            .errorMessage(String.join("; ", errorFieldMessageEntry.getValue()))
                            .build()
            );

        }

        return validationErrorResponseDto;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponseDto handleConstraintValidationException(ConstraintViolationException e) {
        ValidationErrorResponseDto validationErrorResponseDto = new ValidationErrorResponseDto();

        for (ConstraintViolation violation : e.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            validationErrorResponseDto.getValidationErrors().add(
                    ValidationErrorDto.builder()
                            .fieldName(fieldName)
                            .errorMessage(errorMessage)
                            .build()
            );
        }

        return validationErrorResponseDto;
    }

    @ExceptionHandler(value = {AuthenticationException.class, RefreshTokenException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public void handleAuthenticationException(RuntimeException e,
                                              HttpServletRequest request) {
        String remoteAddress = request.getRemoteAddr();

        StringBuilder errorMessageBuilder = new StringBuilder(e.getMessage());
        errorMessageBuilder.append(", remoteAddress: ");
        errorMessageBuilder.append(remoteAddress);
        log.error(e.toString(), e);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public void handleAccessDeniedException(AccessDeniedException e,
                                            HttpServletRequest request) {
        String remoteAddress = request.getRemoteAddr();
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        StringBuilder errorMessageBuilder = new StringBuilder(e.getMessage());
        errorMessageBuilder.append(", username: ");
        errorMessageBuilder.append(username);
        errorMessageBuilder.append(", remoteAddress: ");
        errorMessageBuilder.append(remoteAddress);

        log.error(errorMessageBuilder.toString(), e);
    }

    @ExceptionHandler(value = {Exception.class, Error.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public UnexpectedErrorDto handleUnexpectedException(Throwable e) {
        String errorMessage = "An unexpected error has occurred.";

        log.error(errorMessage, e);

        return UnexpectedErrorDto.builder()
                .errorMessage(errorMessage)
                .build();
    }
}