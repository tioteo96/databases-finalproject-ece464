package com.packet.indoor.service;

import com.packet.indoor.domain.packet.Packet;
import com.packet.indoor.repository.packet.PacketRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Random;

@AllArgsConstructor
@Service
public class PacketService {

    @Autowired
    private PacketRepository packetRepository;

//    @Scheduled(fixedRate = 100)
    public void generateRandomPackets(String tagId, Integer quantity){
//        Random random = new Random();
//        for (int i=0; i<quantity; i++) {
//            Instant instant = Instant.now();
//            Double randomVal1 = 0.0 + (100.0) * random.nextDouble();
//            Double randomVal2 = 0.0 + (100.0) * random.nextDouble();
//            Double randomVal3 = 0.0 + (100.0) * random.nextDouble();
//            Packet packet1 = Packet.create(tagId, "TestAnchorA", randomVal1, instant);
//            Packet packet2 = Packet.create(tagId, "TestAnchorB", randomVal2, instant);
//            Packet packet3 = Packet.create(tagId, "TestAnchorC", randomVal3, instant);
//
//            packetRepository.save(packet1);
//            packetRepository.save(packet2);
//            packetRepository.save(packet3);
//        }
        Random random = new Random();
        // TODO: find all list of assignedTags that are active
        //       find all list of anchors that are active
        //       iterate through the assignedTags
        //          for each assignedTags create packets with all anchors and save
    }
}
