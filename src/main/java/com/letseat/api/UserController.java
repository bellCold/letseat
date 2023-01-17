package com.letseat.api;

import com.letseat.api.requset.UserSignUpRequestDto;
import com.letseat.api.requset.UserUpdateRequestDto;
import com.letseat.api.response.SuccessResponseDto;
import com.letseat.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.letseat.api.response.SuccessCode.SUCCESS_CREATE_USER;
import static com.letseat.api.response.SuccessCode.SUCCESS_UPDATE_USER;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<SuccessResponseDto> signup(@Valid @RequestBody UserSignUpRequestDto userSignUpRequestDto) {
        userService.createUser(userSignUpRequestDto);
        return SuccessResponseDto.toResponseEntity(SUCCESS_CREATE_USER);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SuccessResponseDto> update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        userService.update(id, userUpdateRequestDto);
        return SuccessResponseDto.toResponseEntity(SUCCESS_UPDATE_USER);
    }

    @DeleteMapping("/leaveId")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
