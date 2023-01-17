package com.letseat.application;

import com.letseat.api.exception.ErrorCode;
import com.letseat.api.exception.LetsEatException;
import com.letseat.api.requset.WriteContentsRequestDto;
import com.letseat.domain.board.Board;
import com.letseat.domain.board.BoardRepository;
import com.letseat.domain.user.UserAccount;
import com.letseat.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.letseat.api.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public void writeContents(Long id, WriteContentsRequestDto writeContentsRequestDto) {
        UserAccount userAccount = userRepository.findById(id).orElseThrow(() -> new LetsEatException(USER_NOT_FOUND));
        Board board = Board.builder()
                .title(writeContentsRequestDto.getTitle())
                .contents(writeContentsRequestDto.getContents())
                .userAccount(userAccount)
                .build();

        boardRepository.save(board);
    }
}
