package com.packet.indoor.repository.packet;

import com.packet.indoor.domain.packet.Packet;

import java.time.LocalDateTime;
import java.util.List;

public interface PacketRepository {
    void save(Packet packet);

    List<Packet> findPackets(String tagId, LocalDateTime from, LocalDateTime to);

    List<Packet> findAllPackets(String tagId);
}
