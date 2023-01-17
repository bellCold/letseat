package com.letseat.api.requset;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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

    @Builder
    public UserSignUpRequestDto(String nickname, String password, String email, String address, String phone) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }
}
