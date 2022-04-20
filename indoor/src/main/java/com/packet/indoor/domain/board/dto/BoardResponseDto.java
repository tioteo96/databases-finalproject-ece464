package com.packet.indoor.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class BoardResponseDto {
    private String boardName;
}
