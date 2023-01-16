package com.letseat.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByNickname(String nickname);

    boolean existsByNicknameOrEmail(String nickname, String email);
}
