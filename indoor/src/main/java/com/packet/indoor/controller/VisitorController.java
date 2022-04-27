package com.packet.indoor.controller;

import com.packet.indoor.domain.assignedBoard.dto.AssignedBoardUserResponseDto;
import com.packet.indoor.domain.location.dto.LocationRequestDto;
import com.packet.indoor.service.VisitorService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public void exportCsvFile(@RequestBody(required = false) LocationRequestDto requestDto, HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("application/octet-stream");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"locations.xlsx\"");

        visitorService.exportCsvFile(requestDto, servletResponse);
    }
}
