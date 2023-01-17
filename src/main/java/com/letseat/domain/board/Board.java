package com.letseat.domain.board;

import com.letseat.domain.BaseTimeEntity;
import com.letseat.domain.user.UserAccount;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class LestEatBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount userAccount;
}
