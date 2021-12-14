package com.packet.indoor.controller;

import com.packet.indoor.domain.user.dto.UserResponseDto;
import com.packet.indoor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserResponseDto>> findAllUsers(){
        List<UserResponseDto> response = userService.findAllActiveUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
