package com.letseat.api.requset;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class UserSignUpRequestDto {

    @NotBlank
    @Length(min = 3, max = 20)
    private String nickname;

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;

}
