package com.letseat.application;

import com.letseat.api.exception.LetsEatException;
import com.letseat.api.requset.UserSignUpRequestDto;
import com.letseat.api.requset.UserUpdateRequestDto;
import com.letseat.domain.user.Account;
import com.letseat.domain.user.AccountRepository;
import com.letseat.domain.user.UserAccount;
import com.letseat.domain.user.UserRole;
import com.letseat.global.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.letseat.api.exception.ErrorCode.DUPLICATE_RESOURCE;
import static com.letseat.api.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void createUser(UserSignUpRequestDto userSignUpRequestDto) {
        validate(userSignUpRequestDto.getNickname(), userSignUpRequestDto.getEmail());

        Account newAccount = Account.builder()
                .nickname(userSignUpRequestDto.getNickname())
                .password(passwordEncoder.encode(userSignUpRequestDto.getPassword()))
                .phone(userSignUpRequestDto.getPhone())
                .email(userSignUpRequestDto.getEmail())
                .address(userSignUpRequestDto.getAddress())
                .userGrade(UserRole.BASIC)
                .build();

        accountRepository.save(newAccount);

        newAccount.updateToken(jwtProvider);
    }

    private void validate(String nickname, String email) {
        if (accountRepository.existsByNicknameOrEmail(nickname, email)) {
            throw new LetsEatException(DUPLICATE_RESOURCE);
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

    @Override
    public UserDetails loadUserByUsername(String nicknameOrEmail) throws UsernameNotFoundException {
        Account account = accountRepository.findByNicknameOrEmail(nicknameOrEmail, nicknameOrEmail).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        return new UserAccount(account);
    }
}
