package com.packet.indoor.domain.assignedBoard;

import com.packet.indoor.domain.assignedBoard.dto.AssignedBoardResponseDto;
import com.packet.indoor.domain.assignedBoard.dto.AssignedBoardUserResponseDto;
import com.packet.indoor.domain.board.Board;
import com.packet.indoor.domain.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder(builderClassName = "Builder")
@Getter
@AllArgsConstructor
@Table(name = "assigned_tag")
@Entity
public class AssignedBoard {

    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "assigned_board_id")
    @Id
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "is_assigned", nullable = false)
    private Boolean assigned;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    @Column(name = "unassigned_at")
    private LocalDateTime unAssignedAt;

    protected AssignedBoard(){}

    public static AssignedBoard create(Visitor visitor, Board board){
        board.assignBoard();
        return AssignedBoard.builder()
                .visitor(visitor)
                .board(board)
                .assigned(true)
                .assignedAt(LocalDateTime.now())
                .build();
    }

    public void unAssign() {
        this.assigned = false;
        this.unAssignedAt = LocalDateTime.now();
        this.board.unAssignBoard();
    }

    public String getInfluxTag() {
        return this.board.getBoardName().substring(2).toLowerCase();
    }

    public AssignedBoardResponseDto toResponseDto() {
        return AssignedBoardResponseDto.builder()
                .boardName(this.board.getBoardName())
                .username(this.visitor.getUsername())
                .assignedAt(this.assignedAt)
                .build();
    }

    public AssignedBoardUserResponseDto toUserResponseDto() {
        return AssignedBoardUserResponseDto.builder()
                .id(this.id.toString())
                .username(this.visitor.getUsername())
                .visitorType(this.visitor.getType())
                .build();
    }
}
