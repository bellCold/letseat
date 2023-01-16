package com.letseat.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "해당 유저는 이미 존재합니다.");

    private final HttpStatus httpStatus;
    private final String errorMsg;

}
