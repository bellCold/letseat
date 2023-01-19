/*
package com.letseat.api;

import com.letseat.api.requset.WriteContentsRequestDto;
import com.letseat.application.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/write")
    public ResponseEntity<Void> writeContents( @RequestBody WriteContentsRequestDto writeContentsRequestDto) {
        boardService.writeContents(writeContentsRequestDto);
        return ResponseEntity.ok().build();
    }
}
*/
