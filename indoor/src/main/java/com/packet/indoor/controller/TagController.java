package com.packet.indoor.controller;

import java.util.List;
import java.util.Optional;

import com.packet.indoor.domain.tag.Tag;
import com.packet.indoor.domain.tag.TagId;
import com.packet.indoor.repository.tag.TagRepository;

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
public class TagController {
    
    @Autowired
    private TagRepository tagRepository;

    @GetMapping(value = "tags")
    public ResponseEntity<List<Tag>> get_tag_list(){
        return new ResponseEntity<>(tagRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "tag/{id}")
    public ResponseEntity<Tag> get_tag(@PathVariable("id") Long id){
        TagId tag_id = new TagId();
        tag_id.setId(id);
        Optional<Tag> opt_tag = tagRepository.findById(tag_id);
        if (!opt_tag.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Tag tag = opt_tag.get();
        return new ResponseEntity<>(tag, HttpStatus.OK);
    } 

    //TODO: FIX THIS
    @PostMapping(value = "tag")
    public ResponseEntity<Tag> create_tag(@RequestBody Tag tag){
        Tag created_tag = tagRepository.save(tag);
        return new ResponseEntity<>(created_tag, HttpStatus.OK);
    }
}
