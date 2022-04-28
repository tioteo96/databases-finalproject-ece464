package com.packet.indoor.controller;

import com.packet.indoor.domain.assignedBoard.dto.AssignedBoardUserResponseDto;
import com.packet.indoor.domain.location.dto.LocationRequestDto;
import com.packet.indoor.exception.IllegalActionException;
import com.packet.indoor.service.VisitorService;
import com.packet.indoor.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
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

    @GetMapping("/csv")
    public void exportCsvFile(@RequestBody(required = false) LocationRequestDto requestDto,
                              @RequestParam(required = false) String from,
                              @RequestParam(required = false) String to,
                              HttpServletResponse servletResponse) throws IOException {

        LocalDateTime fromUTC = null;
        LocalDateTime toUTC = null;

        if (from != null && to != null) {
            ZoneId est = ZoneId.of("America/New_York");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(est);
            LocalDateTime fromEST = LocalDate.parse(from, formatter).atTime(LocalTime.MIN);
            LocalDateTime toEST = LocalDate.parse(to, formatter).atTime(LocalTime.MAX);
            if (fromEST.isAfter(toEST)) throw new IllegalActionException(ErrorMessage.INVALID_DATE);

            fromUTC = fromEST.atZone(est).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            toUTC = toEST.atZone(est).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        }

        servletResponse.setContentType("application/octet-stream");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"locations.xlsx\"");

        visitorService.exportCsvFile(requestDto, fromUTC, toUTC, servletResponse);
    }
}
