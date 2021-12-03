package com.packet.indoor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnchorController {
    
    @GetMapping(value = "/api/anchor/{id}")
    public String get_anchor(@PathVariable String id){
        //TODO: change return datatype
        return "get anchor id: " + id;
    }

    @PostMapping(value = "/api/anchor", consumes = "application/json", produces = "application/json")
    public String create_anchor(@RequestBody String anchor){
        //TODO: change return and body datatypes
        return "create anchor with id: ";
    }
}
