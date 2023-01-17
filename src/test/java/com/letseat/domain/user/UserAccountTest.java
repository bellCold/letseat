package com.letseat.domain.user;

import com.letseat.api.exception.LetsEatException;
import com.letseat.api.requset.UserSignUpRequestDto;
import com.letseat.api.requset.UserUpdateRequestDto;
import com.letseat.application.UserService;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class UserAccountTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;

    private UserAccount userAccount;

    @BeforeEach
    public void createAccount() {
        userAccount = UserAccount.builder()
                .nickname("nickname")
                .password(passwordEncoder.encode("12345678"))
                .userGrade(BASIC)
                .address("seoul")
                .phone("010-1234-5678")
                .email("Success@gmail.com")
                .build();

        userRepository.save(userAccount);
    }

    @DisplayName("유저 생성 테스트")
    @Test
    public void userCreateTest() {
        UserAccount findUser = userRepository.findByNickname("nickname").orElseThrow(() -> new LetsEatException(DUPLICATE_RESOURCE));
        assertThat("nickname").isEqualTo(findUser.getNickname());
        assertThat("whdcks420").isNotEqualTo(findUser.getNickname());
        assertThat(passwordEncoder.matches("12345678", findUser.getPassword())).isTrue();
    }

    @DisplayName("회원 정보 수정 테스트")
    @Test
    public void updateUserAccount() {
        UserAccount userAccount = userRepository.findByNickname("nickname").orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        userService.update(userAccount.getId(), new UserUpdateRequestDto("11112222", "경기도", "010-5678-1234"));

        assertThat(userAccount.getAddress()).isEqualTo("경기도");
        assertThat(userAccount.getPhone()).isEqualTo("010-5678-1234");
        assertThat(passwordEncoder.matches("11112222", userAccount.getPassword())).isTrue();
    }

    @DisplayName("이메일 또는 닉네임 확인 테스트 성공")
    @Test
    public void findUserByNickOrEmailSuccess() {
        assertThat(userRepository.existsByNicknameOrEmail("nickname", null)).isTrue();
        assertThat(userRepository.existsByNicknameOrEmail(null, "Success@gmail.com")).isTrue();
    }

    @DisplayName("이메일 또는 닉네임 확인 테스트 실패")
    @Test
    public void findUserByNickOrEmailFail() {
        assertThat(userRepository.existsByNicknameOrEmail("Fail", null)).isFalse();
        assertThat(userRepository.existsByNicknameOrEmail(null, "Fail@gmail.com")).isFalse();
    }

    @DisplayName("이메일 또는 닉네임 중복가입일 경우 오류 테스트")
    @Test
    public void duplicateCreateAccountTest() {
        UserSignUpRequestDto userSignUpRequestDto = UserSignUpRequestDto.builder()
                .nickname("nickname")
                .email("Success@gmail.com")
                .build();

        assertThatThrownBy(() -> userService.createUser(userSignUpRequestDto)).isInstanceOf(LetsEatException.class);
    }

    @DisplayName("회원 탈퇴 테스트")
    @Test
    public void leaveUserTest() {
        UserAccount findUser = userRepository.findById(userAccount.getId()).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        userService.delete(findUser.getId());

        assertThatThrownBy(() -> userRepository.findById(findUser.getId()).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND)))
                .isInstanceOf(LetsEatException.class);
    }
}