package com.packet.indoor.controller;

import java.util.List;
import java.util.Optional;

import com.packet.indoor.domain.packet.Packet;
import com.packet.indoor.repository.packet.PacketRepository;

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
public class PacketController {
    
//    @Autowired
//    private PacketRepository packetRepository;
//
//    @GetMapping(value = "packets")
//    public ResponseEntity<List<Packet>> get_packet_list(){
//        return new ResponseEntity<>(packetRepository.findAll(), HttpStatus.OK);
//    }
//
//    @GetMapping(value = "packet/{id}")
//    public ResponseEntity<Packet> get_packet(@PathVariable("id") Long id){
//        PacketId packet_id = new PacketId();
//        packet_id.setId(id);
//        Optional<Packet> opt_packet = packetRepository.findById(packet_id);
//        if (!opt_packet.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        Packet packet = opt_packet.get();
//        return new ResponseEntity<>(packet, HttpStatus.OK);
//    }
//
//    @PostMapping(value = "packet")
//    public ResponseEntity<Packet> create_packet(@RequestBody Packet packet){
//        Packet created_packet = packetRepository.save(packet);
//        return new ResponseEntity<>(created_packet, HttpStatus.CREATED);
//    }
}
