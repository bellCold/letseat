package com.letseat.api.requset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class DeleteUserDto {
    @NotBlank
    @Length(min = 8, max = 50)
    private String password;
}
