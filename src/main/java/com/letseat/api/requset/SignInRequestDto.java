package com.letseat.api.requset;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.validation.constraints.NotBlank;

@Getter
public class SignInRequestDto {
    @NotBlank
    @Length(min = 3, max = 20)
    private String nickname;

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;

    public Authentication toAuthentication() {
        return new UsernamePasswordAuthenticationToken(this.nickname, this.password);
    }
}
