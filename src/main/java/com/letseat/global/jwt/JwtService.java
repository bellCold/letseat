package com.letseat.global.jwt;

import com.letseat.api.requset.SignInRequestDto;
import com.letseat.api.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    public TokenResponseDto generateToken(Long id, SignInRequestDto signInRequestDto) {
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(signInRequestDto.toAuthentication());
        return jwtProvider.generateToken(id,authentication);
    }
}
