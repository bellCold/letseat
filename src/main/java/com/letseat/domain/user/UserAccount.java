package com.letseat.domain.user;

import com.letseat.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class UserAccount extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;

    private String email;

    private String phone;

    private String address;

    @Enumerated(value = EnumType.STRING)
    private UserRole userGrade;

    @Builder
    public UserAccount(String nickname, String email, String phone, String address) {
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
