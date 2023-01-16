package com.letseat.api.response;

import lombok.Getter;

@Getter
public class UserSignUpDto {

    private String nickname;

    private String password;

    private String email;

    private String address;

    private String phone;

}
