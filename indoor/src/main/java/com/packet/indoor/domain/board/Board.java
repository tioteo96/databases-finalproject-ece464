package com.packet.indoor.domain.board;

import com.packet.indoor.domain.BaseEntity;
import com.packet.indoor.domain.board.dto.BoardResponseDto;
import com.packet.indoor.exception.IllegalActionException;
import com.packet.indoor.util.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Builder(builderClassName = "Builder")
@Getter
@AllArgsConstructor
@Table(name = "board")
@Entity
public class Board extends BaseEntity {

    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "board_id")
    @Id
    private UUID id;

    @Column(name = "board_name", nullable = false, unique = true)
    private String boardName;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_status", nullable = false)
    private BoardStatus boardStatus;

    protected Board(){}

    public static Board create(String boardName) {
        return Board.builder()
                .boardName(boardName)
                .boardStatus(BoardStatus.AVAILABLE)
                .build();
    }

    public void assignBoard() {
        if (this.boardStatus.equals(BoardStatus.ASSIGNED)) throw new IllegalActionException(ErrorMessage.BOARD_ALREADY_ASSIGNED);
        this.boardStatus = BoardStatus.ASSIGNED;
    }

    public void unAssignBoard() {
        if (this.boardStatus.equals(BoardStatus.AVAILABLE)) throw new IllegalActionException(ErrorMessage.BOARD_ALREADY_UNASSIGNED);
        this.boardStatus = BoardStatus.AVAILABLE;
    }

    public BoardResponseDto toResponseDto() {
        return BoardResponseDto.builder()
                .boardName(this.boardName)
                .build();
    }
}
