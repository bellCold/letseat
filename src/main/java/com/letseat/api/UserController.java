package com.letseat.api;

import com.letseat.api.requset.SignUpRequestDto;
import com.letseat.api.validator.SignUpValidator;
import com.letseat.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SignUpValidator signUpValidator;

    @InitBinder("signUpRequestDto")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpValidator);
    }

    @GetMapping("/login")
    public String loginView() {
        return "account/login";
    }

    @GetMapping("/join")
    public String signUpView() {
        return "account/sign-up";
    }

    @PostMapping("/join")
    public String signUpSubmit(@Valid SignUpRequestDto signUpRequestDto, Errors errors) {
        if (errors.hasErrors()) {
            return "/users/join";
        }
        userService.createUser(signUpRequestDto);
        return "redirect:/";
    }

//    @PutMapping("/update")
//    public ResponseEntity<SuccessResponseDto> update( @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
//        userService.update(id, userUpdateRequestDto);
//        return SuccessResponseDto.toResponseEntity(SUCCESS_UPDATE_USER);
//    }

//    @DeleteMapping("/leave")
//    public ResponseEntity<Void> delete(@LoginUserId Long id, @Valid @RequestBody DeleteUserDto deleteUserDto) {
//        userService.delete(id, deleteUserDto);
//        return ResponseEntity.ok().build();
//    }
}
