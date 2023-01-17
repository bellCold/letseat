package com.letseat.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    SUCCESS_CREATE_USER(HttpStatus.OK, "회원가입 성공"),
    SUCCESS_UPDATE_USER(HttpStatus.OK, "회원정보 수정 성공");

    private final HttpStatus httpStatus;
    private final String successMsg;
}
