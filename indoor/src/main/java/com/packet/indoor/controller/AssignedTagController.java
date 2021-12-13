package com.packet.indoor.controller;

import java.util.List;
import java.util.Optional;

import com.packet.indoor.domain.assignedTag.AssignedTag;
import com.packet.indoor.domain.assignedTag.AssignedTagId;
import com.packet.indoor.repository.assignedTag.AssignedTagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class AssignedTagController {
    
    @Autowired
    private AssignedTagRepository assignedTagRepository;

    @GetMapping(value = "assigned-tags")
    public ResponseEntity<List<AssignedTag>> get_assigned_tags_list(){
        return new ResponseEntity<>(assignedTagRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "assiged-tags/{id}")
    public ResponseEntity<AssignedTag> get_assigned_tag(@PathVariable("id") Long id){
        AssignedTagId assigned_tag_id = new AssignedTagId();
        assigned_tag_id.setId(id);
        Optional<AssignedTag> opt_assigned_tag = assignedTagRepository.findById(assigned_tag_id);
        if (!opt_assigned_tag.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        AssignedTag assigned_tag = opt_assigned_tag.get();
        return new ResponseEntity<>(assigned_tag, HttpStatus.OK);
    }

    //TODO: retire old assigned_tag with same tagId -> register new assigned_tag
    @PostMapping(value = "assigned-tag")
    public ResponseEntity<AssignedTag> create_assigned_tag(@RequestBody AssignedTag assigned_tag){
        AssignedTag created_assigned_tag = assignedTagRepository.save(assigned_tag);
        return new ResponseEntity<>(created_assigned_tag, HttpStatus.CREATED);
    }
}
