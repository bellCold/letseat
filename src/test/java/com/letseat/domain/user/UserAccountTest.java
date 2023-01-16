package com.letseat.domain.user;

import com.letseat.api.exception.LetsEatException;
import com.letseat.api.requset.UserUpdateRequestDto;
import com.letseat.application.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static com.letseat.api.exception.ErrorCode.DUPLICATE_RESOURCE;
import static com.letseat.api.exception.ErrorCode.USER_NOT_FOUND;
import static com.letseat.domain.user.UserRole.BASIC;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class UserAccountTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;

    @BeforeEach
    public void createAccount() {
        UserAccount userAccount = UserAccount.builder()
                .nickname("bellCold")
                .password(passwordEncoder.encode("12345678"))
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
        UserAccount findUser = userRepository.findByNickname("bellCold").orElseThrow(() -> new LetsEatException(DUPLICATE_RESOURCE));
        assertThat("bellCold").isEqualTo(findUser.getNickname());
        assertThat("whdcks420").isNotEqualTo(findUser.getNickname());
        assertThat(passwordEncoder.matches("12345678", findUser.getPassword())).isTrue();
    }

    @DisplayName("이메일 또는 닉네임으로 유저 찾기 성공")
    @Test
    public void findUserByNickOrEmailSuccess() {
        assertThat(userRepository.existsByNicknameOrEmail("bellCold", null)).isTrue();
        assertThat(userRepository.existsByNicknameOrEmail(null, "whdcks420@gmail.com")).isTrue();
    }

    @DisplayName("이메일 또는 닉네임으로 유저 찾기 실패")
    @Test
    public void findUserByNickOrEmailFail() {
        assertThat(userRepository.existsByNicknameOrEmail("bellColdFail", null)).isFalse();
        assertThat(userRepository.existsByNicknameOrEmail(null, "whdcks420@gmail.comFail")).isFalse();
    }

    @DisplayName("회원 정보 수정 테스트")
    @Test
    public void updateUserAccount() {
        UserAccount userAccount = userRepository.findById(1L).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        userService.update(1L, new UserUpdateRequestDto("11112222", "경기도 시흥시띵띵띵", "010-5678-1234"));

        assertThat(userAccount.getAddress()).isEqualTo("경기도 시흥시띵띵띵");
        assertThat(userAccount.getPhone()).isEqualTo("010-5678-1234");
        assertThat(passwordEncoder.matches("11112222", userAccount.getPassword())).isTrue();
    }
}