package com.letseat.api.validator;

import com.letseat.api.requset.SignUpRequestDto;
import com.letseat.domain.user.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpRequestDto signUpRequestDto = (SignUpRequestDto) target;
        if (accountRepository.existsByEmail(signUpRequestDto.getEmail())) {
            errors.rejectValue("email", "invalid.email", new Object[]{signUpRequestDto.getEmail()}, "이미 존재하는 이메일 입니다.");
        }
    }
}
