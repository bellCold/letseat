package com.letseat.api.requset;

import com.letseat.domain.user.Account;
import com.letseat.domain.user.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

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
    public SignUpRequestDto(String nickname, String password, String email, String address, String phone) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }


    public Account toEntity() {
        return Account.builder()
                .nickname(this.nickname)
                .address(this.address)
                .password(this.password)
                .email(this.email)
                .phone(this.phone)
                .userGrade(UserRole.BASIC)
                .build();
    }
}
