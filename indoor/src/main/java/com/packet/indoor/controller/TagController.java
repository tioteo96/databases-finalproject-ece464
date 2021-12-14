package com.packet.indoor.controller;

import java.util.List;

import com.packet.indoor.domain.tag.Tag;

import com.packet.indoor.domain.tag.dto.TagCreateRequestDto;
import com.packet.indoor.domain.tag.dto.TagResponseDto;
import com.packet.indoor.service.TagService;
import com.packet.indoor.util.TagStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/tag")
@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping(value = "/register")
    public ResponseEntity<TagResponseDto> registerTag(@RequestBody TagCreateRequestDto requestDto){
        TagResponseDto responseDto = tagService.registerNewTag(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //TODO: assign a tag to a user and send error if tag is already occupied
    @PostMapping(value = "/assign")
    public ResponseEntity<?> assignTag(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    //TODO: unassign a tag attached to a user and send error if tag is not assigned
    @PutMapping(value = "/unassign")
    public ResponseEntity<?> unassignTag(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> findAllTags(){
        List<TagResponseDto> responseDtos = tagService.findAllTags();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/all/available")
    public ResponseEntity<?> findAvailableTags(){
        TagStatus tagStatus = TagStatus.AVAILABLE;
        List<TagResponseDto> responseDtos = tagService.findAllAvailableTags(tagStatus);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK); 
    }

    //TODO: find all tags that are currently unavailable
    @GetMapping(value = "/all/unavailable")
    public ResponseEntity<?> findUnAvailableTags(){
        TagStatus tagStatus = TagStatus.AVAILABLE;
        List<TagResponseDto> responseDtos = tagService.findAllUnavailableTags(tagStatus);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

//    @GetMapping(value = "tag/{id}")
//    public ResponseEntity<Tag> get_tag(@PathVariable("id") Long id){
//        TagId tag_id = new TagId();
//        tag_id.setId(id);
//        Optional<Tag> opt_tag = tagRepository.findById(tag_id);
//        if (!opt_tag.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        Tag tag = opt_tag.get();
//        return new ResponseEntity<>(tag, HttpStatus.OK);
//    }
}
