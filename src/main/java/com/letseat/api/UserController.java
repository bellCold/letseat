package com.letseat.api;

import com.letseat.api.requset.UserSignUpRequestDto;
import com.letseat.api.requset.UserUpdateRequestDto;
import com.letseat.api.response.SuccessCode;
import com.letseat.api.response.SuccessResponseDto;
import com.letseat.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.letseat.api.response.SuccessCode.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<SuccessResponseDto> signUp(@Valid @RequestBody UserSignUpRequestDto userSignUpRequestDto) {
        userService.createUser(userSignUpRequestDto);
        return SuccessResponseDto.toResponseEntity(SUCCESS_CREATE_USER);
    }

//    @PutMapping("/update")
//    public ResponseEntity<Void> change(@Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
//
//    }
}
