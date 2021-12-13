package com.packet.indoor.controller;

import java.util.List;
import java.util.Optional;

import com.packet.indoor.domain.anchor.Anchor;
import com.packet.indoor.domain.anchor.AnchorId;
import com.packet.indoor.repository.anchor.AnchorRepository;

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
@RequestMapping("/api/anchor")
public class AnchorController {
    
//    @Autowired
//    private AnchorRepository anchorRepository;

    //TODO: register a new anchor only through the admin user
    @PostMapping(value = "/register")
    public ResponseEntity<?> registerAnchor(@RequestBody Anchor anchor){
//        Anchor created_anchor = anchorRepository.save(anchor);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    //TODO: find all anchors
    @GetMapping(value = "/all")
    public ResponseEntity<List<Anchor>> findAllAnchors(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

//    @GetMapping(value = "anchor/{id}")
//    public ResponseEntity<Anchor> get_anchor(@PathVariable("id") Long id){
//        AnchorId anchor_id = new AnchorId();
//        anchor_id.setId(id);
//        Optional<Anchor> opt_anchor = anchorRepository.findById(anchor_id);
//        if (!opt_anchor.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        Anchor anchor = opt_anchor.get();
//        System.out.println(anchor.toString());
//        return new ResponseEntity<>(anchor, HttpStatus.OK);
//    }
}
