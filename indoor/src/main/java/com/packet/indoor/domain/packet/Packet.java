package com.packet.indoor.domain.packet;

import com.packet.indoor.domain.BaseEntity;
import com.packet.indoor.domain.anchor.Anchor;
import com.packet.indoor.domain.assignedTag.AssignedTag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import javax.persistence.*;

@Builder(builderClassName = "Builder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "packet")
@Entity
public class Packet extends BaseEntity{
    
    @EmbeddedId
    private PacketId packetId;

    @ManyToOne
    @JoinColumn(name = "anchorId")
    private Anchor anchor;

    @ManyToOne
    @JoinColumn(name = "assignedTagId")
    private AssignedTag assignedTag;

    @Column(nullable = false)
    private LocalDateTime receivedTime;

    @Column(nullable = false)
    private Double distance;

    public static Packet create(Anchor anchor, AssignedTag assignedTag, LocalDateTime receivedTime, Double distance) {
        return Packet.builder()
                .anchor(anchor)
                .assignedTag(assignedTag)
                .receivedTime(receivedTime)
                .distance(distance)
                .build();
    }
}
