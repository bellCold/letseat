package com.letseat.api;

import com.letseat.api.requset.SignInRequestDto;
import com.letseat.api.requset.SignUpRequestDto;
import com.letseat.api.requset.UserUpdateRequestDto;
import com.letseat.api.response.SuccessResponseDto;
import com.letseat.api.response.TokenResponseDto;
import com.letseat.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.letseat.api.response.SuccessCode.SUCCESS_CREATE_USER;
import static com.letseat.api.response.SuccessCode.SUCCESS_UPDATE_USER;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponseDto> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        userService.createUser(signUpRequestDto);
        return SuccessResponseDto.toResponseEntity(SUCCESS_CREATE_USER);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> signIn(@Valid @RequestBody SignInRequestDto signInRequestDto) {
        return ResponseEntity.ok().body(userService.signIn(signInRequestDto));
    }

    @PutMapping("/update")
    public ResponseEntity<SuccessResponseDto> update(@Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        userService.update(userUpdateRequestDto);
        return SuccessResponseDto.toResponseEntity(SUCCESS_UPDATE_USER);
    }
}
