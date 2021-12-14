package com.packet.indoor.repository.packet;

import com.packet.indoor.domain.packet.Packet;
import com.packet.indoor.domain.packet.TagPackets;

import java.time.LocalDateTime;
import java.util.List;

public interface PacketRepository {
    void save(Packet packet);

    void save(List<Packet> packets);

    List<Packet> findAllPackets(String tagId);

    List<TagPackets> findTagPackets(String tagId, LocalDateTime from, LocalDateTime to, Integer quantity);
}
