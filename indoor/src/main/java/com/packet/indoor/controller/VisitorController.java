package com.packet.indoor.controller;

import com.packet.indoor.domain.assignedBoard.dto.AssignedBoardUserResponseDto;
import com.packet.indoor.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/visitor")
@RestController
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @GetMapping
    public ResponseEntity<List<AssignedBoardUserResponseDto>> recentVisitors() {
        List<AssignedBoardUserResponseDto> responseDto = visitorService.recentVisitors();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
