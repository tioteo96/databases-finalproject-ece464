package com.packet.indoor.service;

import com.packet.indoor.domain.packet.Packet;
import com.packet.indoor.repository.packet.PacketRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@AllArgsConstructor
@Service
public class PacketService {

    @Autowired
    private PacketRepository packetRepository;

    @Transactional
    public void testSavePacket(){
//        Packet packet = Packet.create("TagA", "AnchorB", Double.valueOf(20.0));
//        packetRepository.save(packet);
        packetRepository.findAllPackets("TagA");
    }
}
