package com.letseat.api.requset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {

    @Length(min = 8, max = 50)
    private String newPassword;

    private String newAddress;

    private String newPhone;

    public UserUpdateRequestDto(String newPassword, String address, String phone) {
        this.newPassword = newPassword;
        this.newAddress = address;
        this.newPhone = phone;
    }
}
