package com.letseat.application;

import com.letseat.api.requset.SignInRequestDto;
import com.letseat.global.jwt.JwtProvider;
import com.letseat.api.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    public TokenResponseDto generateToken(SignInRequestDto signInRequestDto) {
        AuthenticationManager authenticationManager = authenticationManagerBuilder.getObject();
        Authentication authentication = authenticationManager.authenticate(signInRequestDto.toAuthentication());
        return jwtProvider.generateToken(authentication);
    }
}
