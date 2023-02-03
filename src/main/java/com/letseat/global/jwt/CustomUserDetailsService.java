package com.letseat.global.jwt;

import com.letseat.domain.user.Account;
import com.letseat.domain.user.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        return accountRepository.findByNickname(nickname)
                .map(this::toUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private UserDetails toUserDetails(final Account account) {
        return User.builder()
                .username(account.getNickname())
                .password(account.getPassword())
                .authorities(account.getUserGrade().toString())
                .build();
    }
}
