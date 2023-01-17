package com.letseat.application;

import com.letseat.api.exception.LetsEatException;
import com.letseat.api.requset.WriteContentsRequestDto;
import com.letseat.domain.board.Board;
import com.letseat.domain.board.BoardRepository;
import com.letseat.domain.user.Account;
import com.letseat.domain.user.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.letseat.api.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final AccountRepository accountRepository;
    private final BoardRepository boardRepository;

    public void writeContents(Long id, WriteContentsRequestDto writeContentsRequestDto) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        Board board = Board.builder()
                .title(writeContentsRequestDto.getTitle())
                .contents(writeContentsRequestDto.getContents())
                .account(account)
                .build();

        boardRepository.save(board);
    }
}
