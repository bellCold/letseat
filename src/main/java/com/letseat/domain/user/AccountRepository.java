package com.letseat.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNickname(String nickname);

    boolean existsByNicknameOrEmail(String nickname, String email);

    Optional<Account> findByNicknameOrEmail(String nicknameOrEmail, String orEmail);
}
