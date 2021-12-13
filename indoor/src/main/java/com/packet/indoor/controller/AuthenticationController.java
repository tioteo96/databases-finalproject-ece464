package com.packet.indoor.controller;

import com.packet.indoor.domain.user.Username;
import com.packet.indoor.domain.user.dto.LoginRequestDto;
import com.packet.indoor.domain.user.dto.LoginResponseDto;
import com.packet.indoor.domain.user.dto.UserCreateRequestDto;
import com.packet.indoor.domain.user.dto.UserCreateResponseDto;
import com.packet.indoor.service.AuthenticationService;
import com.packet.indoor.service.PacketService;
import com.packet.indoor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PacketService packetService;

    @PostMapping("/signUp")
    public ResponseEntity<UserCreateResponseDto> signUpUser(@RequestBody UserCreateRequestDto requestDto) {
        UserCreateResponseDto responseDto = userService.createAdmin(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDto requestDto) {
        Username username = Username.create(requestDto.getUsername(), requestDto.getGroupname());
        String rawPassword = requestDto.getPassword();
        LoginResponseDto responseDto = authenticationService.verifyAndGenerateJsonWebToken(username, rawPassword);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
