package com.letseat.application;

import com.letseat.api.exception.LetsEatException;
import com.letseat.api.requset.UserSignUpRequestDto;
import com.letseat.domain.user.UserAccount;
import com.letseat.domain.user.UserRepository;
import com.letseat.domain.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.letseat.api.exception.ErrorCode.DUPLICATE_RESOURCE;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserSignUpRequestDto userSignUpRequestDto) {
        validate(userSignUpRequestDto.getNickname(), userSignUpRequestDto.getEmail());

        UserAccount newUserAccount = UserAccount.builder()
                .nickname(userSignUpRequestDto.getNickname())
                .password(passwordEncoder.encode(userSignUpRequestDto.getPassword()))
                .phone(userSignUpRequestDto.getPhone())
                .address(userSignUpRequestDto.getAddress())
                .userGrade(UserRole.BASIC)
                .build();

        userRepository.save(newUserAccount);
    }

    private void validate(String nickname, String email) {
        if (userRepository.existsByNicknameOrEmail(nickname, email)) {
            throw new LetsEatException(DUPLICATE_RESOURCE);
        }
    }
}
