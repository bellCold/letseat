/*
package com.letseat.domain.user;

import com.letseat.api.exception.LetsEatException;
import com.letseat.api.requset.DeleteUserDto;
import com.letseat.api.requset.SignUpRequestDto;
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
class AccountTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;

    private Account account;

    @BeforeEach
    public void createAccount() {
        account = Account.builder()
                .password(passwordEncoder.encode("12345678"))
                .userGrade(BASIC)
                .address("seoul")
                .phone("010-1234-5678")
                .email("Success@gmail.com")
                .build();

        accountRepository.save(account);
    }

    @DisplayName("유저 생성 테스트")
    @Test
    public void userCreateTest() {
        Account findUser = accountRepository.findByNickname("nickname").orElseThrow(() -> new LetsEatException(DUPLICATE_RESOURCE));
        assertThat("nickname").isEqualTo(findUser.getNickname());
        assertThat("whdcks420").isNotEqualTo(findUser.getNickname());
        assertThat(passwordEncoder.matches("12345678", findUser.getPassword())).isTrue();
    }

    @DisplayName("회원 정보 수정 테스트")
    @Test
    public void updateUserAccount() {
        Account account = accountRepository.findByNickname("nickname").orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        userService.update(account.getId(), new UserUpdateRequestDto("11112222", "경기도", "010-5678-1234"));

        assertThat(account.getAddress()).isEqualTo("경기도");
        assertThat(account.getPhone()).isEqualTo("010-5678-1234");
        assertThat(passwordEncoder.matches("11112222", account.getPassword())).isTrue();
    }

    @DisplayName("이메일 또는 닉네임 확인 테스트 성공")
    @Test
    public void findUserByNickOrEmailSuccess() {
        assertThat(accountRepository.existsEmail("nickname", null)).isTrue();
        assertThat(accountRepository.existsEmail(null, "Success@gmail.com")).isTrue();
    }

    @DisplayName("이메일 또는 닉네임 확인 테스트 실패")
    @Test
    public void findUserByNickOrEmailFail() {
        assertThat(accountRepository.existsEmail("Fail", null)).isFalse();
        assertThat(accountRepository.existsEmail(null, "Fail@gmail.com")).isFalse();
    }

    @DisplayName("이메일 또는 닉네임 중복가입일 경우 오류 테스트")
    @Test
    public void duplicateCreateAccountTest() {
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .nickname("nickname")
                .email("Success@gmail.com")
                .build();

        assertThatThrownBy(() -> userService.createUser(signUpRequestDto)).isInstanceOf(LetsEatException.class);
    }

    @DisplayName("회원 탈퇴 테스트")
    @Test
    public void leaveUserTest() {
        userService.delete(account.getId(), new DeleteUserDto("12345678"));
        assertThatThrownBy(() -> accountRepository.findById(account.getId()).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND)))
                .isInstanceOf(LetsEatException.class);
    }

    @DisplayName("회원 탈퇴 실패 테스트")
    @Test
    public void fail_LeaveUserTest() {
        assertThatThrownBy(() -> userService.delete(account.getId(), new DeleteUserDto("123412678"))).isInstanceOf(LetsEatException.class);
    }

}*/
