package com.letseat.api.requset;

import com.letseat.domain.user.Account;
import com.letseat.domain.user.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;

    @NotBlank
    @Length(min = 8, max = 50)
    private String passwordConfirm;

    public Account toEntity() {
        return Account.builder()
                .password(this.password)
                .email(this.email)
                .userGrade(UserRole.BASIC)
                .build();
    }
}
