package com.letseat.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST,"비밀번호가 틀렸습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저가 없습니다."),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "해당 유저는 이미 존재합니다.");

    private final HttpStatus httpStatus;
    private final String errorMsg;
}
