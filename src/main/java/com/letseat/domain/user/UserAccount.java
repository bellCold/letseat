package com.letseat.domain.user;

import com.letseat.api.requset.UserUpdateRequestDto;
import com.letseat.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserAccount extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Enumerated(value = EnumType.STRING)
    private UserRole userGrade;

    @Builder
    public UserAccount(String nickname, String password, String email, String phone, String address, UserRole userGrade) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.userGrade = userGrade;
    }

    public void change(UserUpdateRequestDto userUpdateRequestDto, String newPassword) {
        this.password = newPassword;
        this.address = userUpdateRequestDto.getAddress();
        this.phone = userUpdateRequestDto.getPhone();
    }
}
