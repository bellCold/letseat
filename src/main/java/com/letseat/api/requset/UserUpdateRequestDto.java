package com.letseat.api.requset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class UserUpdateRequestDto {

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;
}
