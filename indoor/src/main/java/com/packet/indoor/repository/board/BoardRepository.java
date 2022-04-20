package com.packet.indoor.repository.board;

import com.packet.indoor.domain.board.Board;
import com.packet.indoor.domain.board.BoardStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID> {
    List<Board> findAllByBoardStatus(BoardStatus boardStatus);
    Optional<Board> findByBoardName(String boardName);
}
