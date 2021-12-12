package com.packet.indoor.domain.packet;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder(builderClassName = "Builder")
@Getter
@AllArgsConstructor
@Measurement(name="packet")
public class Packet {

    @Column(timestamp = true)
    private Instant time;

    @Column(tag = true)
    private String tag;

    @Column(tag = true)
    private String anchor;

    @Column(name = "signal_strength")
    private Double signalStrength;

    public static Packet create(String tagId, String anchorId, Double signalStrength){
        return Packet.builder()
                .time(Instant.now())
                .tag(tagId)
                .anchor(anchorId)
                .signalStrength(signalStrength)
                .build();
    }
}
