package com.letseat.global.exception;

import com.letseat.api.exception.LetsEatException;
import com.letseat.api.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.letseat.api.exception.ErrorCode.DUPLICATE_RESOURCE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponseDto> handleDataException() {
        log.error("handleDataException throw Exception : {}", DUPLICATE_RESOURCE);
        return ErrorResponseDto.toResponseEntity(DUPLICATE_RESOURCE);
    }

    @ExceptionHandler(value = {LetsEatException.class})
    protected ResponseEntity<ErrorResponseDto> handleCustomException(LetsEatException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponseDto.toResponseEntity(e.getErrorCode());
    }
}
