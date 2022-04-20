package com.packet.indoor.domain.assignedBoard.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.packet.indoor.util.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class AssignedBoardResponseDto {
    private String boardName;
    private String username;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime assignedAt;
}
