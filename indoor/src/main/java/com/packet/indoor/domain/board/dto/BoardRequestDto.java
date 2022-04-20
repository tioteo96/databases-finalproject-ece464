package com.packet.indoor.domain.board.dto;

import com.packet.indoor.domain.board.Board;
import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String boardName;

    public Board toEntity() {
        return Board.create(this.boardName);
    }
}
