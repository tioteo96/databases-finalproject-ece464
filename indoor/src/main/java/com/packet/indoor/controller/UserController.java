package com.packet.indoor.controller;

import java.util.List;
import java.util.Optional;

import com.packet.indoor.domain.user.User;
import com.packet.indoor.domain.user.Username;
import com.packet.indoor.repository.user.JpaUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RestController
public class UserController {

//    @Autowired
//    private JpaUserRepository jpaUserRepository;

    //TODO: find all users with role ROLE_USER
    @GetMapping(value = "/all")
    public ResponseEntity<?> findAllUsers(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

//
//    @GetMapping(value = "/api/user/{username}")
//    public ResponseEntity<User> get_user(@PathVariable("id") Username username){
//        Optional<User> opt_user = jpaUserRepository.findUserByUsername(username);
//        if (!opt_user.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        User user = opt_user.get();
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
//
//
//    @PostMapping(value = "/api/user")
//    public ResponseEntity<User> create_user(@RequestBody User user){
//        User created_user = jpaUserRepository.save(user);
//        return new ResponseEntity<>(created_user, HttpStatus.CREATED);
//    }
}
