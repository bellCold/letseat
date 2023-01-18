package com.letseat.application;

import com.letseat.api.exception.LetsEatException;
import com.letseat.api.requset.SignInRequestDto;
import com.letseat.api.requset.SignUpRequestDto;
import com.letseat.api.requset.UserUpdateRequestDto;
import com.letseat.api.response.SignInResponseDto;
import com.letseat.domain.user.Account;
import com.letseat.domain.user.AccountRepository;
import com.letseat.domain.user.UserAccount;
import com.letseat.global.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.letseat.api.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void createUser(SignUpRequestDto signUpRequestDto) {
        validate(signUpRequestDto.getNickname(), signUpRequestDto.getEmail());

        Account account = signUpRequestDto.toEntity();
        account.encodePassword(passwordEncoder);
        accountRepository.save(account);
    }

    private void validate(String nickname, String email) {
        if (accountRepository.existsByNicknameOrEmail(nickname, email)) {
            throw new LetsEatException(DUPLICATE_RESOURCE);
        }
    }

    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        Account account = accountRepository.findByNickname(signInRequestDto.getNickname()).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        if (passwordEncoder.matches(signInRequestDto.getPassword(), account.getPassword())) {
            return new SignInResponseDto(jwtProvider.generateToken(signInRequestDto.getNickname()), jwtProvider.generateRefreshToken(signInRequestDto.getNickname()));
        } else {
            throw new LetsEatException(PASSWORD_VERIFY);
        }
    }

    @Transactional
    public void update(Long id, UserUpdateRequestDto userUpdateRequestDto) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        account.change(userUpdateRequestDto, passwordEncoder.encode(userUpdateRequestDto.getPassword()));
    }

    @Transactional
    public void delete(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        accountRepository.delete(account);
    }

}
