package com.packet.indoor.service;

import com.packet.indoor.domain.assignedBoard.AssignedBoard;
import com.packet.indoor.domain.assignedBoard.dto.AssignedBoardUserResponseDto;
import com.packet.indoor.repository.assignedBoard.AssignedBoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class VisitorService {

    private AssignedBoardRepository assignedBoardRepository;

    public List<AssignedBoardUserResponseDto> recentVisitors() {
        List<AssignedBoard> assignedBoards = assignedBoardRepository.findByOrderByAssignedAtDesc();
        List<AssignedBoardUserResponseDto> responseDtos = assignedBoards.stream()
                .map(assignedBoard -> assignedBoard.toUserResponseDto())
                .collect(Collectors.toList());

        return responseDtos;
    }
}
