package com.packet.indoor.controller;

import com.packet.indoor.domain.assignedBoard.dto.AssignedBoardRequestDto;
import com.packet.indoor.domain.assignedBoard.dto.AssignedBoardResponseDto;
import com.packet.indoor.domain.board.dto.BoardRequestDto;
import com.packet.indoor.domain.board.dto.BoardResponseDto;
import com.packet.indoor.exception.IllegalActionException;
import com.packet.indoor.service.BoardService;
import com.packet.indoor.util.ErrorMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/board")
@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping(value = "/available")
    public ResponseEntity<List<BoardResponseDto>> availableBoards() {
        List<BoardResponseDto> availableBoards = boardService.findAvailableBoards();
        return new ResponseEntity<>(availableBoards, HttpStatus.OK);
    }

    @GetMapping(value = "/occupied")
    public ResponseEntity<List<AssignedBoardResponseDto>> occupiedBoards() {
        List<AssignedBoardResponseDto> occupiedBoards = boardService.findOccupiedBoards();
        return new ResponseEntity<>(occupiedBoards, HttpStatus.OK);
    }

    @PostMapping(value = "/assign")
    public ResponseEntity<AssignedBoardResponseDto> assignBoard(@RequestBody AssignedBoardRequestDto requestDto) {
        if (StringUtils.isEmpty(requestDto.getBoardName())) throw new IllegalActionException(ErrorMessage.BOARD_NAME_REQUIRED);
        if (StringUtils.isEmpty(requestDto.getUsername())) throw new IllegalActionException(ErrorMessage.USERNAME_REQUIRED);
        if (requestDto.getVisitorType() == null) throw new IllegalActionException(ErrorMessage.VISITOR_TYPE_REQUIRED);

        AssignedBoardResponseDto responseDto = boardService.assignBoard(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping(value = "/unassign")
    public ResponseEntity<BoardResponseDto> unAssignBoard(@RequestParam String boardName) {
        BoardResponseDto responseDto = boardService.unAssignedBoard(boardName);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto requestDto) {
        BoardResponseDto responseDto = boardService.createBoard(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
