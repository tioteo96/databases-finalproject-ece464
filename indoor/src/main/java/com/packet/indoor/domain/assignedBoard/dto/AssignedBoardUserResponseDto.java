package com.packet.indoor.domain.assignedBoard.dto;

import com.packet.indoor.domain.visitor.VisitorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class AssignedBoardUserResponseDto {
    private String id;
    private String username;
    private VisitorType visitorType;
}
