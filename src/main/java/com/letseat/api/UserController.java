package com.letseat.api;

import com.letseat.api.requset.DeleteUserDto;
import com.letseat.api.requset.SignInRequestDto;
import com.letseat.api.requset.SignUpRequestDto;
import com.letseat.api.requset.UserUpdateRequestDto;
import com.letseat.api.response.SuccessResponseDto;
import com.letseat.api.response.TokenResponseDto;
import com.letseat.application.UserService;
import com.letseat.global.config.interceptor.LoginUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.letseat.api.response.SuccessCode.SUCCESS_UPDATE_USER;


@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginView() {
        return "account/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@Valid SignInRequestDto signInRequestDto, Model model, Errors errors) {
        TokenResponseDto token = userService.signIn(signInRequestDto);
//        if (errors.hasErrors()) {
//            return "/users/login";
//        }
        model.addAttribute(token);
        return "redirect:/";
    }

    @GetMapping("/join")
    public String signUpView() {
        return "account/sign-up";
    }

    @PostMapping("/join")
    public String signUpSubmit(@Valid SignUpRequestDto signUpRequestDto) {
        userService.createUser(signUpRequestDto);
        return "redirect:/";
    }

    @PutMapping("/update")
    public ResponseEntity<SuccessResponseDto> update(@LoginUserId Long id, @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        userService.update(id, userUpdateRequestDto);
        return SuccessResponseDto.toResponseEntity(SUCCESS_UPDATE_USER);
    }

    @DeleteMapping("/leave")
    public ResponseEntity<Void> delete(@LoginUserId Long id, @Valid @RequestBody DeleteUserDto deleteUserDto) {
        userService.delete(id, deleteUserDto);
        return ResponseEntity.ok().build();
    }
}
