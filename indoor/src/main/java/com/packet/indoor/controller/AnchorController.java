package com.packet.indoor.controller;

import java.util.List;

import com.packet.indoor.domain.anchor.Anchor;

import com.packet.indoor.domain.anchor.dto.AnchorRequestDto;
import com.packet.indoor.domain.anchor.dto.AnchorResponseDto;
import com.packet.indoor.service.AnchorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/anchor")
public class AnchorController {

    @Autowired
    private AnchorService anchorService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerAnchor(@RequestBody AnchorRequestDto requestDto){
        AnchorResponseDto responseDto = anchorService.registerNewAnchor(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<AnchorResponseDto>> findAllAnchors(@RequestParam String manufacturer){
        List<AnchorResponseDto> responseDtos = anchorService.findAllAnchorsOfManufacturer(manufacturer);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }
}
