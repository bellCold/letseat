package com.letseat.application;

import com.letseat.api.exception.LetsEatException;
import com.letseat.api.response.UserSignUpDto;
import com.letseat.domain.user.UserAccount;
import com.letseat.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.letseat.api.exception.ErrorCode.DUPLICATE_RESOURCE;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserSignUpDto userSignUpDto) {
        if (userSignUpDto == null) {
            throw new RuntimeException();
        }

        validate(userSignUpDto.getNickname(), userSignUpDto.getEmail());

        UserAccount newUserAccount = UserAccount.toEntity(userSignUpDto);

        userRepository.save(newUserAccount);
    }

    private void validate(String nickname, String email) {
        if (userRepository.existsByNicknameOrEmail(nickname, email)) {
            throw new LetsEatException(DUPLICATE_RESOURCE);
        }
    }
}
