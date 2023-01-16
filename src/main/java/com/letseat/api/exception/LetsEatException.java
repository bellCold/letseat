package com.letseat.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LetsEatException extends RuntimeException {
    private final ErrorCode errorCode;
}
