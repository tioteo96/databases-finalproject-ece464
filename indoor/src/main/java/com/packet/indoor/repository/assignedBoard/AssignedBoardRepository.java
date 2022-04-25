package com.packet.indoor.repository.assignedBoard;

import com.packet.indoor.domain.assignedBoard.AssignedBoard;
import com.packet.indoor.domain.board.Board;
import com.packet.indoor.domain.visitor.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssignedBoardRepository extends JpaRepository<AssignedBoard, UUID> {
    List<AssignedBoard> findAllByAssignedIsTrue();
    Optional<AssignedBoard> findByBoardAndAssignedIsTrue(Board board);
    Optional<AssignedBoard> findByVisitorAndAssignedIsTrue(Visitor visitor);
    List<AssignedBoard> findByOrderByAssignedAtDesc();
    Optional<AssignedBoard> findTopByAssignedIsFalseOrderByAssignedAtDesc();
}
