package com.letseat.api;

import com.letseat.api.requset.UserSignUpRequestDto;
import com.letseat.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@Valid @RequestBody UserSignUpRequestDto userSignUpRequestDto) {
        userService.createUser(userSignUpRequestDto);
        return ResponseEntity.ok().build();
    }
}
