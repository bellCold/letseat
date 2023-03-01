package com.letseat.api.requset;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WriteContentsRequestDto {
    private String title;
    private String contents;
}
