package com.packet.indoor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {
    
    @GetMapping(value = "/api/tag/{id}")
    public String get_tag(@PathVariable String id){
        //TODO: change return datatype
        return "tag id: " + id;
    } 

    @PostMapping(value = "/api/tag", consumes = "application/json", produces = "application/json")
    public String create_tag(@RequestBody String tag){
        //TODO: change return and body datatypes
        return "create tag with id: ";
    }
}
