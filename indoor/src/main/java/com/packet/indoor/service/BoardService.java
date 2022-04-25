package com.packet.indoor.service;

import com.packet.indoor.domain.assignedBoard.AssignedBoard;
import com.packet.indoor.domain.assignedBoard.dto.AssignedBoardRequestDto;
import com.packet.indoor.domain.assignedBoard.dto.AssignedBoardResponseDto;
import com.packet.indoor.domain.board.Board;
import com.packet.indoor.domain.board.BoardStatus;
import com.packet.indoor.domain.board.dto.BoardRequestDto;
import com.packet.indoor.domain.board.dto.BoardResponseDto;
import com.packet.indoor.domain.visitor.Visitor;
import com.packet.indoor.exception.IllegalActionException;
import com.packet.indoor.exception.NotFoundException;
import com.packet.indoor.repository.assignedBoard.AssignedBoardRepository;
import com.packet.indoor.repository.board.BoardRepository;
import com.packet.indoor.repository.visitor.VisitorRepository;
import com.packet.indoor.util.ErrorMessage;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BoardService {

    private BoardRepository boardRepository;
    private VisitorRepository visitorRepository;
    private AssignedBoardRepository assignedBoardRepository;

    public List<BoardResponseDto> findAvailableBoards() {
        List<Board> boards = boardRepository.findAllByBoardStatus(BoardStatus.AVAILABLE);
        List<BoardResponseDto> responseDtos = boards.stream()
                .map(board -> board.toResponseDto())
                .collect(Collectors.toList());

        return responseDtos;
    }

    public List<AssignedBoardResponseDto> findOccupiedBoards() {
        List<AssignedBoard> assignedBoards = assignedBoardRepository.findAllByAssignedIsTrue();
        List<AssignedBoardResponseDto> responseDtos = assignedBoards.stream()
                .map(assignedBoard -> assignedBoard.toResponseDto())
                .collect(Collectors.toList());

        return responseDtos;
    }

    @Transactional
    public AssignedBoardResponseDto assignBoard(AssignedBoardRequestDto requestDto) {
        Visitor visitor = null;
        if (StringUtils.isNotEmpty(requestDto.getEmail())) {
            Optional<Visitor> visitorOptional = visitorRepository.findByEmail(requestDto.getEmail());
            if (visitorOptional.isEmpty()) {
                visitor = requestDto.toEntity();
                visitorRepository.save(visitor);
            } else {
                visitor = visitorOptional.get();
                Optional<AssignedBoard> assignedBoardOptional = assignedBoardRepository.findByVisitorAndAssignedIsTrue(visitor);
                if (assignedBoardOptional.isPresent()) throw new IllegalActionException(ErrorMessage.VISITOR_ALREADY_ASSIGNED);
            }
        } else {
            visitor = requestDto.toEntity();
            visitorRepository.save(visitor);
        }

        Optional<Board> boardOptional = boardRepository.findByBoardName(requestDto.getBoardName());
        if (boardOptional.isEmpty()) throw new NotFoundException(ErrorMessage.BOARD_NOT_FOUND);
        Board board = boardOptional.get();

        AssignedBoard assignedBoard = AssignedBoard.create(visitor, board);
        assignedBoardRepository.save(assignedBoard);
        return assignedBoard.toResponseDto();
    }

    @Transactional
    public BoardResponseDto unAssignedBoard(String boardName) {
        Optional<Board> boardOptional = boardRepository.findByBoardName(boardName);
        if (boardOptional.isEmpty()) throw new NotFoundException(ErrorMessage.BOARD_NOT_FOUND);
        Board board = boardOptional.get();

        if (board.getBoardStatus().equals(BoardStatus.AVAILABLE)) throw new IllegalActionException(ErrorMessage.BOARD_ALREADY_UNASSIGNED);

        Optional<AssignedBoard> assignedBoardOptional = assignedBoardRepository.findByBoardAndAssignedIsTrue(board);
        if (assignedBoardOptional.isEmpty()) throw new IllegalActionException(ErrorMessage.DATA_NOT_SYNCED);
        AssignedBoard assignedBoard = assignedBoardOptional.get();

        assignedBoard.unAssign();
        return board.toResponseDto();
    }

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        Board newBoard = requestDto.toEntity();
        boardRepository.save(newBoard);
        return newBoard.toResponseDto();
    }
}
