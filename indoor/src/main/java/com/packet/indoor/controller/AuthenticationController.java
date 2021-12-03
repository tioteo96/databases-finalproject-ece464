package com.packet.indoor.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@RequestMapping("api/v1/authenticate")
@RestController
public class AuthenticationController {



//    @PostMapping
//    public ResponseEntity<AuthenticationResponseDto> authenticateUser(@RequestBody AuthenticationRequestDto requestDto, HttpServletRequest request) {
//        request.setAttribute("email", requestDto.getEmail());
//        AuthenticationResponseDto responseDto = authenticationService.verifyAndCreateJsonWebToken(requestDto.getEmail(), requestDto.getPassword());
//        return new ResponseEntity<>(responseDto, HttpStatus.OK);
//    }
}
