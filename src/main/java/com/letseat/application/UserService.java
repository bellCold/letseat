package com.letseat.application;

import com.letseat.api.exception.LetsEatException;
import com.letseat.api.requset.DeleteUserDto;
import com.letseat.api.requset.SignUpRequestDto;
import com.letseat.api.requset.UserUpdateRequestDto;
import com.letseat.domain.user.Account;
import com.letseat.domain.user.AccountRepository;
import com.letseat.domain.user.UserAccount;
import com.letseat.domain.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.letseat.api.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(SignUpRequestDto signUpRequestDto) {
        validate(signUpRequestDto.getEmail());

        Account account = signUpRequestDto.toEntity();
        account.encodePassword(passwordEncoder);
        accountRepository.save(account);
        log.info("{}님이 회원가입이 하였습니다.", signUpRequestDto.getEmail());

        login(account);
    }

    private void validate(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new LetsEatException(DUPLICATE_RESOURCE);
        }
    }

    public void update(Long id, UserUpdateRequestDto userUpdateRequestDto) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        account.change(userUpdateRequestDto, passwordEncoder.encode(userUpdateRequestDto.getNewPassword()));
    }

    public void delete(Long id, DeleteUserDto deleteUserDto) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        if (!passwordEncoder.matches(deleteUserDto.getPassword(), account.getPassword())) {
            throw new LetsEatException(PASSWORD_MISMATCH);
        }
        accountRepository.delete(account);
    }

    public void login(Account account) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority(UserRole.BASIC.toString())));
        SecurityContextHolder.getContext().setAuthentication(token);

        log.info("{}님이 로그인 하였습니다.", account.getEmail());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        return new UserAccount(account);
    }
}
