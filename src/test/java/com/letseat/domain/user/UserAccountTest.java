package com.letseat.domain.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.letseat.domain.user.UserRole.BASIC;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserAccountTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void createAccount() {
        UserAccount userAccount = UserAccount.builder()
                .nickname("bellCold")
                .password("1234")
                .userGrade(BASIC)
                .address("seoul")
                .phone("010-1234-5678")
                .email("whdcks420@gmail.com")
                .build();

        userRepository.save(userAccount);
    }

    @AfterEach
    public void deleteAccount() {
        userRepository.deleteAll();
    }

    @DisplayName("유저 생성 테스트")
    @Test
    public void userCreateTest() {
        UserAccount findUser = userRepository.findByNickname("bellCold").orElseThrow(RuntimeException::new);
        assertThat("bellCold").isEqualTo(findUser.getNickname());
        assertThat("whdcks420").isNotEqualTo(findUser.getNickname());
    }

    @DisplayName("이메일 또는 닉네임으로 유저 찾기")
    @Test
    public void findUserByNickOrEmail() {
        userRepository.existsByNicknameOrEmail("bellCold", null);
        userRepository.existsByNicknameOrEmail(null, "whdcks420@gmail.com");
        assertThat(userRepository.existsByNicknameOrEmail("bellCold", null)).isTrue();
        assertThat(userRepository.existsByNicknameOrEmail(null, "whdcks420@gmail.com")).isTrue();
    }
}