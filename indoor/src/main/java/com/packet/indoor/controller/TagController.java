package com.packet.indoor.controller;

import com.packet.indoor.domain.assignedTag.dto.AssignedTagResponseDto;
import com.packet.indoor.domain.tag.Tag;

import com.packet.indoor.domain.tag.dto.TagCreateRequestDto;
import com.packet.indoor.domain.tag.dto.TagResponseDto;
import com.packet.indoor.service.AssignedTagService;
import com.packet.indoor.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/tag")
@RestController
public class TagController {

    @Autowired
    private TagService tagService;
    @Autowired
    private AssignedTagService assignedTagService;

    @PostMapping(value = "/register")
    public ResponseEntity<TagResponseDto> registerTag(@RequestBody TagCreateRequestDto requestDto){
        TagResponseDto responseDto = tagService.registerNewTag(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //TODO: assign a tag to a user and send error if tag is already occupied
    @PostMapping(value = "/assign")
    public ResponseEntity<AssignedTagResponseDto> assignTag(@RequestParam String tagId, @RequestParam String username){
        AssignedTagResponseDto responseDto = assignedTagService.assignTagToUser(UUID.fromString(tagId), username);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //TODO: unassign a tag attached to a user and send error if tag is not assigned
    @PutMapping(value = "/unassign")
    public ResponseEntity<?> unassignTag(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    //TODO: find all tags
    @GetMapping(value = "/all")
    public ResponseEntity<?> findAllTags(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    //TODO: find all tags that are available for assigning to user
    @GetMapping(value = "/all/available")
    public ResponseEntity<?> findAvailableTags(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    //TODO: find all tags that are currently unavailable
    @GetMapping(value = "/all/unavailable")
    public ResponseEntity<?> findUnAvailableTags(){
        return new ResponseEntity<>(null, HttpStatus.OK);
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
