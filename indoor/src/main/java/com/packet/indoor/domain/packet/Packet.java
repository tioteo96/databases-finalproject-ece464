package com.packet.indoor.domain.packet;

import com.packet.indoor.domain.BaseEntity;
import com.packet.indoor.domain.anchor.Anchor;
import com.packet.indoor.domain.assignedTag.AssignedTag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

import javax.persistence.*;

@Builder(builderClassName = "Builder")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "packet")
@Entity
public class Packet extends BaseEntity{
    
    @EmbeddedId
    private PacketId packetId;
    @Embedded
    @NonNull
    @OneToOne
    private Anchor anchor;
    @Embedded
    @NonNull
    @OneToOne
    private AssignedTag assignedTag;
    @Embedded
    @NonNull
    private LocalDateTime receivedTime;
    @Embedded
    private double distance;

    public static Packet create(Anchor anchor, AssignedTag assignedTag, LocalDateTime receivedTime, double distance) {
        return Packet.builder()
                .anchor(anchor)
                .assignedTag(assignedTag)
                .receivedTime(receivedTime)
                .distance(distance)
                .build();
    }
}
