package com.packet.indoor.service;

import com.packet.indoor.domain.location.Location;
import com.packet.indoor.domain.assignedBoard.AssignedBoard;
import com.packet.indoor.domain.assignedBoard.dto.AssignedBoardUserResponseDto;
import com.packet.indoor.domain.location.dto.LocationRequestDto;
import com.packet.indoor.domain.visitor.VisitorType;
import com.packet.indoor.repository.assignedBoard.AssignedBoardRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
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

    public void exportCsvFile(LocationRequestDto requestDto, LocalDateTime from, LocalDateTime to, HttpServletResponse servletResponse) throws IOException {
        ServletOutputStream outputStream = servletResponse.getOutputStream();

        Workbook workbook = new XSSFWorkbook();
        Map<VisitorType, Sheet> sheets = createSheets(workbook);
        Map<VisitorType, Integer> rowCounts = createRowCounts();

        List<AssignedBoard> assignedBoards = getAssignedBoards(requestDto, from, to);

        for (AssignedBoard assignedBoard : assignedBoards) {
            List<Location> locations = influxService.findLocationOfAssignedBoard(assignedBoard);
            for (Location location : locations) {
                writeLocation(location, sheets, rowCounts);
            }
        }

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private List<AssignedBoard> getAssignedBoards(LocationRequestDto requestDto, LocalDateTime from, LocalDateTime to) {
        List<AssignedBoard> assignedBoards = new ArrayList<>();

        if (from == null || to == null) {
            if (requestDto == null || requestDto.getIds().isEmpty()) {
                assignedBoards.addAll(assignedBoardRepository.findAll());
            } else {
                List<UUID> uuids = requestDto.getIds().stream()
                        .map(s -> UUID.fromString(s))
                        .collect(Collectors.toList());
                assignedBoards.addAll(assignedBoardRepository.findByIdIn(uuids));
            }
        } else {
//            LocalDate fromLocalDate = fromDate.toInstant().atZone(ZoneId.of("America/New_York")).toLocalDate();
//            LocalDate toLocalDate = toDate.toInstant().atZone(ZoneId.of("America/New_York")).toLocalDate();
//            ZoneId test = ZoneId.systemDefault();
//            LocalDate fromLocalDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            LocalDate toLocalDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            LocalDateTime from = fromLocalDate.atTime(LocalTime.MIN);
//            LocalDateTime to = toLocalDate.atTime(LocalTime.MAX);

            List<AssignedBoard> boards = assignedBoardRepository.findAllByAssignedAtBetween(from, to);
            assignedBoards.addAll(boards);
        }

        return assignedBoards;
    }

    private void writeLocation(Location location, Map<VisitorType, Sheet> sheets, Map<VisitorType, Integer> rowCounts) {
        if (location.getVisitorType().equals(VisitorType.STUDENT)) {
            writeRow(location, sheets.get(VisitorType.STUDENT), rowCounts.get(VisitorType.STUDENT));
            rowCounts.put(VisitorType.STUDENT, rowCounts.get(VisitorType.STUDENT) + 1);

        } else if (location.getVisitorType().equals(VisitorType.FACULTY)) {
            writeRow(location, sheets.get(VisitorType.FACULTY), rowCounts.get(VisitorType.FACULTY));
            rowCounts.put(VisitorType.FACULTY, rowCounts.get(VisitorType.FACULTY) + 1);

        } else {
            writeRow(location, sheets.get(VisitorType.GUEST), rowCounts.get(VisitorType.GUEST));
            rowCounts.put(VisitorType.GUEST, rowCounts.get(VisitorType.GUEST) + 1);
        }
    }

    private void writeRow(Location location, Sheet sheet, Integer rowCount) {
        Row row = sheet.createRow(rowCount);

        Cell timeCell = row.createCell(0);
        Cell xCell = row.createCell(1);
        Cell yCell = row.createCell(2);
        Cell zCell = row.createCell(3);
        Cell usernameCell = row.createCell(4);

        timeCell.setCellValue(location.getTime());
        xCell.setCellValue(location.getX());
        yCell.setCellValue(location.getY());
        zCell.setCellValue(location.getZ());
        usernameCell.setCellValue(location.getUsername());
    }

    private Map<VisitorType, Integer> createRowCounts() {
        Map<VisitorType, Integer> rowCount = new HashMap<>();
        rowCount.put(VisitorType.STUDENT, 1);
        rowCount.put(VisitorType.FACULTY, 1);
        rowCount.put(VisitorType.GUEST, 1);
        return rowCount;
    }

    private Map<VisitorType, Sheet> createSheets(Workbook workbook) {
        Map<VisitorType, Sheet> sheets = new HashMap<>();
        sheets.put(VisitorType.STUDENT, createSheetAndWriteHeader(workbook, VisitorType.STUDENT));
        sheets.put(VisitorType.FACULTY, createSheetAndWriteHeader(workbook, VisitorType.FACULTY));
        sheets.put(VisitorType.GUEST, createSheetAndWriteHeader(workbook, VisitorType.GUEST));
        return sheets;
    }

    private Sheet createSheetAndWriteHeader(Workbook workbook, VisitorType visitorType) {
        Sheet sheet = workbook.createSheet(visitorType.value());
        Row header = sheet.createRow(0);

        Cell timeCell = header.createCell(0);
        Cell xCell = header.createCell(1);
        Cell yCell = header.createCell(2);
        Cell zCell = header.createCell(3);
        Cell usernameCell = header.createCell(4);

        timeCell.setCellValue("Time");
        xCell.setCellValue("X");
        yCell.setCellValue("Y");
        zCell.setCellValue("Z");
        usernameCell.setCellValue("Username");

        return sheet;
    }
}
