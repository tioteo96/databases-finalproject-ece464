package com.packet.indoor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PacketController {
    
    @GetMapping(value = "/api/packet/{id}")
    public String get_packet(@PathVariable String id){
        //TODO: change return datatype
        return "get packet id: " + id;
    }

    @PostMapping(value = "/api/packet", consumes = "application/json", produces = "application/json")
    public String create_packet(@RequestBody String packet){
        //TODO: change return and body datatypes
        return "create packet with id: ";
    }
}
