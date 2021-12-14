package com.packet.indoor.service;

import com.packet.indoor.domain.anchor.Anchor;
import com.packet.indoor.domain.assignedTag.AssignedTag;
import com.packet.indoor.domain.packet.Packet;
import com.packet.indoor.repository.anchor.AnchorRepository;
import com.packet.indoor.repository.assignedTag.AssignedTagRepository;
import com.packet.indoor.repository.packet.PacketRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Service
public class PacketService {

    private PacketRepository packetRepository;
    private AssignedTagRepository assignedTagRepository;
    private AnchorRepository anchorRepository;

    @Scheduled(fixedRate = 500)
    public void generateRandomPackets(){
        Random random = new Random();
        Instant instant = Instant.now();
        List<AssignedTag> assignedTags = assignedTagRepository.findAllByAssignedIsTrue();
        List<Anchor> anchors = anchorRepository.findAllByManufacturerIgnoreCase("DecaWave");
        List<Packet> packets = new ArrayList<>();
        for (AssignedTag assignedTag : assignedTags) {
            for (Anchor anchor : anchors) {
                Double randomVal = 0.0 + (50.0) * random.nextDouble();
                Packet packet = Packet.create(assignedTag.getId().toString(), anchor.getId().toString(), randomVal, instant);
                packets.add(packet);
                System.out.println(packet.getTag() + packet.getTime().toString());
            }
        }
        if (!packets.isEmpty()) packetRepository.save(packets);
    }
}
