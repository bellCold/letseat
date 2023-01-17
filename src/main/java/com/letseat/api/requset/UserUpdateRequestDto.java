package com.letseat.api.requset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;

    public UserUpdateRequestDto(String password, String address, String phone) {
        this.password = password;
        this.address = address;
        this.phone = phone;
    }
}
