package com.letseat.application;

import com.letseat.api.exception.LetsEatException;
import com.letseat.api.requset.DeleteUserDto;
import com.letseat.api.requset.SignInRequestDto;
import com.letseat.api.requset.SignUpRequestDto;
import com.letseat.api.requset.UserUpdateRequestDto;
import com.letseat.api.response.TokenResponseDto;
import com.letseat.domain.user.Account;
import com.letseat.domain.user.AccountRepository;
import com.letseat.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.letseat.api.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public void createUser(SignUpRequestDto signUpRequestDto) {
        validate(signUpRequestDto.getNickname(), signUpRequestDto.getEmail());

        Account account = signUpRequestDto.toEntity();
        account.encodePassword(passwordEncoder);
        accountRepository.save(account);
        log.info("{}님이 회원가입이 되었습니다.", signUpRequestDto.getNickname());
    }

    private void validate(String nickname, String email) {
        if (accountRepository.existsByNicknameOrEmail(nickname, email)) {
            throw new LetsEatException(DUPLICATE_RESOURCE);
        }
    }

    public TokenResponseDto signIn(SignInRequestDto signInRequestDto) {
        Account account = accountRepository.findByNickname(signInRequestDto.getNickname()).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        if (!passwordEncoder.matches(signInRequestDto.getPassword(), account.getPassword())) {
            throw new LetsEatException(PASSWORD_MISMATCH);
        }
        TokenResponseDto jwtToken = jwtService.generateToken(account.getId(), signInRequestDto);
        log.info("{}님이 로그인 하였습니다.", signInRequestDto.getNickname());
        return TokenResponseDto.builder()
                .grantType(jwtToken.getGrantType())
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .build();
    }

    public void update(Long id, UserUpdateRequestDto userUpdateRequestDto) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        account.change(userUpdateRequestDto, passwordEncoder.encode(userUpdateRequestDto.getNewPassword()));
    }

    public void delete(Long id, DeleteUserDto deleteUserDto) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        if(!passwordEncoder.matches(deleteUserDto.getPassword(), account.getPassword())) {
            throw new LetsEatException(PASSWORD_MISMATCH);
        }
        accountRepository.delete(account);
    }

}
