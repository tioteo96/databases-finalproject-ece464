package com.packet.indoor.repository.packet;

import com.packet.indoor.domain.packet.Packet;
import com.packet.indoor.domain.packet.PacketId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PacketRepository extends JpaRepository<Packet, PacketId>{
    
}
