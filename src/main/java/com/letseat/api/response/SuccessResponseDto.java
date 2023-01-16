package com.letseat.api.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class SuccessResponseDto {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String code;
    private final String message;

    @Builder
    public SuccessResponseDto(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static ResponseEntity<SuccessResponseDto> toResponseEntity(SuccessCode successCode) {
        return ResponseEntity.status(successCode.getHttpStatus())
                .body(SuccessResponseDto.builder()
                        .status(successCode.getHttpStatus().value())
                        .code(successCode.getHttpStatus().name())
                        .message(successCode.getSuccessMsg())
                        .build()
                );
    }
}
