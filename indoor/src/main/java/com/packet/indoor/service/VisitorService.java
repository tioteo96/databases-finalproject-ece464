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
import java.time.LocalDateTime;
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

        List<AssignedBoard> assignedBoards = getAssignedBoards(requestDto, from, to);
        Map<String, Integer> boardColCounts = createBoardColCounts(assignedBoards);

        Workbook workbook = new XSSFWorkbook();
        Map<String, Sheet> sheets = createSheets(workbook, boardColCounts.size());

        Map<LocalDateTime, Integer> dateRowCounts = writeScatter(assignedBoards, sheets, boardColCounts);
        writeVector(assignedBoards, sheets);

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private Map<LocalDateTime, Integer> writeScatter(List<AssignedBoard> assignedBoards, Map<String, Sheet> sheets, Map<String, Integer> boardColCounts) {
        List<Location> locations = new ArrayList<>();
        for (AssignedBoard assignedBoard : assignedBoards) locations.addAll(influxService.findLocationOfAssignedBoard(assignedBoard));
        locations.sort((o1, o2) -> o1.getTime().compareTo(o2.getTime()));
        List<Sheet> scatterSheets = new ArrayList<>();
        scatterSheets.add(sheets.get(VisitorType.STUDENT.value()));
        scatterSheets.add(sheets.get(VisitorType.FACULTY.value()));
        scatterSheets.add(sheets.get(VisitorType.GUEST.value()));
        Map<LocalDateTime, Integer> dateRowCounts = createDateTimeRowCounts(locations, scatterSheets);

        for (Location location : locations) {
            writeLocation(location, sheets, boardColCounts, dateRowCounts);
        }

        return dateRowCounts;
    }

    private void writeVector(List<AssignedBoard> assignedBoards, Map<String, Sheet> sheets) {
        List<AssignedBoard> vectorBoards = createVectorBoards(assignedBoards);
//        for (AssignedBoard assignedBoard : vectorBoards) {
//            System.out.println(assignedBoard.getVisitor().getType().value() + assignedBoard.getVisitor().getUsername() + assignedBoard.getBoard().getBoardName());
//        }
        List<Location> locations = new ArrayList<>();
        for (AssignedBoard assignedBoard : vectorBoards) locations.addAll(influxService.findLocationOfAssignedBoard(assignedBoard));
        locations.sort((o1, o2) -> o1.getTime().compareTo(o2.getTime()));
        List<Sheet> vectorSheets = new ArrayList<>();
        vectorSheets.add(sheets.get(VisitorType.STUDENT.value()+"+17"));
        vectorSheets.add(sheets.get(VisitorType.FACULTY.value()+"+17"));
        vectorSheets.add(sheets.get(VisitorType.GUEST.value()+"+17"));

        LinkedHashMap<LocalDateTime, Integer> dateRowCounts = createDateTimeRowCounts(locations, vectorSheets);
        Map<String, Map<String, Integer>> boardColCounts = createVectorColCounts(vectorBoards);
//        Map<VisitorType(String), Map<BoardName(String), Integer>>
        Map<LocalDateTime, List<Location>> locationsByDateTime = createLocationsByDateTime(locations);

        Map<String, Location> recent = new HashMap<>();
        for (LocalDateTime localDateTime : dateRowCounts.keySet()) {
            for (Location location : locationsByDateTime.get(localDateTime)) recent.put(location.getName(), location);
            for (String name : recent.keySet()) {
                Location location = recent.get(name);
                writeLocation(location, sheets, boardColCounts, dateRowCounts, localDateTime);
            }
        }
        Integer test = 0;
    }

    private void writeLocation(Location location, Map<String, Sheet> sheets, Map<String, Map<String, Integer>> boardColCounts, LinkedHashMap<LocalDateTime, Integer> dateRowCounts, LocalDateTime localDateTime) {
        Sheet sheet = sheets.get(location.getVisitorType().value()+"+17");
        Row row = sheet.getRow(dateRowCounts.get(localDateTime));

        Cell xCell = row.getCell(boardColCounts.get(location.getVisitorType().value()).get(location.getBoardName()));
        Cell yCell = row.getCell(boardColCounts.get(location.getVisitorType().value()).get(location.getBoardName())+1);
        if (xCell == null) xCell = row.createCell(boardColCounts.get(location.getVisitorType().value()).get(location.getBoardName()));
        if (yCell == null) yCell = row.createCell(boardColCounts.get(location.getVisitorType().value()).get(location.getBoardName())+1);
        xCell.setCellValue(location.getX17());
        yCell.setCellValue(location.getY());
    }

    private void writeLocation(Location location, Map<String, Sheet> sheets, Map<String, Integer> boardColCounts, Map<LocalDateTime, Integer> dateRowCounts) {
        Sheet sheet = sheets.get(location.getVisitorType().value());
        Row row = sheet.getRow(dateRowCounts.get(location.getTime()));

        Cell xCell = row.getCell(boardColCounts.get(location.getBoardName()));
        Cell yCell = row.getCell(boardColCounts.get(location.getBoardName())+1);
        if (xCell == null) xCell = row.createCell(boardColCounts.get(location.getBoardName()));
        if (yCell == null) yCell = row.createCell(boardColCounts.get(location.getBoardName())+1);
        xCell.setCellValue(location.getX());
        yCell.setCellValue(location.getY());
    }

    private Map<LocalDateTime, List<Location>> createLocationsByDateTime(List<Location> locations) {
        Map<LocalDateTime, List<Location>> locationsByDateTime = new HashMap<>();
        for (Location location : locations) {
            if (!locationsByDateTime.containsKey(location.getTime())) locationsByDateTime.put(location.getTime(), new ArrayList<>());
            locationsByDateTime.get(location.getTime()).add(location);
        }
        return locationsByDateTime;
    }

    private Map<String, Map<String, Integer>> createVectorColCounts(List<AssignedBoard> vectorBoards) {
        Map<String, Map<String, Integer>> boardColCounts = new HashMap<>();
        for (AssignedBoard assignedBoard : vectorBoards) {
            String visitorType = assignedBoard.getVisitor().getType().value();
            if (!boardColCounts.containsKey(visitorType)) boardColCounts.put(visitorType, new HashMap<>());
            if (boardColCounts.get(visitorType).size() == 0) {
                boardColCounts.get(visitorType).put(assignedBoard.getBoard().getBoardName(), 1);
            } else if (boardColCounts.get(visitorType).size() == 1) {
                boardColCounts.get(visitorType).put(assignedBoard.getBoard().getBoardName(), 3);
            }
        }
        return boardColCounts;
    }

    private List<AssignedBoard> createVectorBoards(List<AssignedBoard> assignedBoards) {
        List<AssignedBoard> students = new ArrayList<>();
        List<AssignedBoard> faculties = new ArrayList<>();
        List<AssignedBoard> guests = new ArrayList<>();
        for (AssignedBoard assignedBoard : assignedBoards) {
            if (assignedBoard.getVisitor().getType().equals(VisitorType.STUDENT) && students.size()<2) {
                students.add(assignedBoard);
            } else if (assignedBoard.getVisitor().getType().equals(VisitorType.FACULTY) && faculties.size()<2) {
                faculties.add(assignedBoard);
            } else if (assignedBoard.getVisitor().getType().equals(VisitorType.GUEST) && guests.size()<2) {
                guests.add(assignedBoard);
            }
        }

        List<AssignedBoard> vectorBoards = new ArrayList<>();
        vectorBoards.addAll(students);
        vectorBoards.addAll(faculties);
        vectorBoards.addAll(guests);

        return vectorBoards;
    }

    private List<AssignedBoard> getAssignedBoards(LocationRequestDto requestDto, LocalDateTime from, LocalDateTime to) {
        List<AssignedBoard> assignedBoards = new ArrayList<>();

        if (from == null || to == null) {
            if (requestDto == null || requestDto.getIds().isEmpty()) {
                assignedBoards.addAll(assignedBoardRepository.findByOrderByAssignedAtAsc());
            } else {
                List<UUID> uuids = requestDto.getIds().stream()
                        .map(s -> UUID.fromString(s))
                        .collect(Collectors.toList());
                assignedBoards.addAll(assignedBoardRepository.findByIdInOrderByAssignedAtAsc(uuids));
            }
        } else {
            List<AssignedBoard> boards = assignedBoardRepository.findAllByAssignedAtBetweenOrderByAssignedAtAsc(from, to);
            assignedBoards.addAll(boards);
        }

        return assignedBoards;
    }

    private Map<String, Integer> createBoardColCounts(List<AssignedBoard> assignedBoards) {
        Map<String, Integer> boardColCounts = new HashMap<>();
        Integer colCount = 1;
        for (AssignedBoard assignedBoard : assignedBoards) {
            if (!boardColCounts.containsKey(assignedBoard.getBoard().getBoardName())) {
                boardColCounts.put(assignedBoard.getBoard().getBoardName(), colCount);
                colCount += 2;
            }
        }
        return boardColCounts;
    }

    private LinkedHashMap<LocalDateTime, Integer> createDateTimeRowCounts(List<Location> locations, List<Sheet> sheets){
        LinkedHashMap<LocalDateTime, Integer> dateTimeRowCounts = new LinkedHashMap<>();
        Integer lastRow = 1;
        for (Location location : locations) {
            if (!dateTimeRowCounts.containsKey(location.getTime())) {
                dateTimeRowCounts.put(location.getTime(), lastRow);
                for (Sheet sheet : sheets) {
                    Row row = sheet.createRow(lastRow);
                    Cell timeCell = row.createCell(0);
                    timeCell.setCellValue(location.getTime().toString());
                }
                lastRow++;
            }
        }
        return dateTimeRowCounts;
    }

    private Map<String, Sheet> createSheets(Workbook workbook, Integer boardCount) {
        Map<String, Sheet> sheets = new HashMap<>();
        sheets.put(VisitorType.STUDENT.value(), createSheetAndWriteHeader(workbook, VisitorType.STUDENT.value(), boardCount));
        sheets.put(VisitorType.FACULTY.value(), createSheetAndWriteHeader(workbook, VisitorType.FACULTY.value(), boardCount));
        sheets.put(VisitorType.GUEST.value(), createSheetAndWriteHeader(workbook, VisitorType.GUEST.value(), boardCount));
        sheets.put(VisitorType.STUDENT.value()+"+17", createSheetAndWriteHeader(workbook, VisitorType.STUDENT.value()+"+17", 2));
        sheets.put(VisitorType.FACULTY.value()+"+17", createSheetAndWriteHeader(workbook, VisitorType.FACULTY.value()+"+17", 2));
        sheets.put(VisitorType.GUEST.value()+"+17", createSheetAndWriteHeader(workbook, VisitorType.GUEST.value()+"+17", 2));
        return sheets;
    }

    private Sheet createSheetAndWriteHeader(Workbook workbook, String visitorType, Integer boardCount) {
        Sheet sheet = workbook.createSheet(visitorType);
        Row header = sheet.createRow(0);

        Cell timeCell = header.createCell(0);
        timeCell.setCellValue("Time");
        int x = 1;
        int y = 2;

        for (int i = 0; i < boardCount; i++) {
            Cell xCell = header.createCell(x);
            Cell yCell = header.createCell(y);
            xCell.setCellValue("X");
            yCell.setCellValue("Y");
            x += 2;
            y += 2;
        }

        return sheet;
    }
}
