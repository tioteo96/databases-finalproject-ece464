package com.packet.indoor.domain.assignedBoard.dto;

import com.packet.indoor.domain.visitor.Visitor;
import com.packet.indoor.domain.visitor.VisitorType;
import lombok.Getter;

@Getter
public class AssignedBoardRequestDto {
    private VisitorType visitorType;
    private String username;
    private String email;
    private String boardName;

    public Visitor toEntity() {
        return Visitor.create(this.visitorType, this.username, this.email);
    }
}
