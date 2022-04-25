package com.packet.indoor.service;

import com.packet.indoor.domain.location.Location;
import com.packet.indoor.domain.assignedBoard.AssignedBoard;
import com.packet.indoor.domain.assignedBoard.dto.AssignedBoardUserResponseDto;
import com.packet.indoor.domain.location.dto.LocationRequestDto;
import com.packet.indoor.repository.assignedBoard.AssignedBoardRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class VisitorService {

    private AssignedBoardRepository assignedBoardRepository;
    private InfluxService influxService;

    public List<AssignedBoardUserResponseDto> recentVisitors() {
        List<AssignedBoard> assignedBoards = assignedBoardRepository.findByOrderByAssignedAtDesc();
        List<AssignedBoardUserResponseDto> responseDtos = assignedBoards.stream()
                .map(assignedBoard -> assignedBoard.toUserResponseDto())
                .collect(Collectors.toList());

        return responseDtos;
    }

    public void exportCsvFile(LocationRequestDto requestDto, Writer writer) throws IOException {
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        csvPrinter.printRecord("Time", "X", "Y", "Z", "Username", "User Type");

        List<AssignedBoard> assignedBoards = new ArrayList<>();
        if (requestDto == null || requestDto.getIds().isEmpty()) {
            assignedBoards.addAll(assignedBoardRepository.findAll());
        } else {
            List<UUID> uuids = requestDto.getIds().stream()
                    .map(s -> UUID.fromString(s))
                    .collect(Collectors.toList());
            assignedBoards.addAll(assignedBoardRepository.findByIdIn(uuids));
        }

        for (AssignedBoard assignedBoard : assignedBoards) {
            List<Location> locations = influxService.findLocationOfAssignedBoard(assignedBoard);
            for (Location location : locations) {
                csvPrinter.printRecord(location.getTime(), location.getX(), location.getY(), location.getZ(), location.getUsername(), location.getVisitorType());
            }
        }
    }
}
