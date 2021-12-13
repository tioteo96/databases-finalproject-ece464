package com.packet.indoor.domain.packet;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.query.FluxRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.time.Instant;

@FieldNameConstants
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

    @Column(name = "signal")
    private Double signal;

    public static Packet create(String tagId, String anchorId, Double signal, Instant instant){
        return Packet.builder()
                .time(instant)
                .tag(tagId)
                .anchor(anchorId)
                .signal(signal)
                .build();
    }

    public static Packet create(FluxRecord record) {
        return Packet.builder()
                .time((Instant) record.getValueByKey("_time"))
                .tag((String) record.getValueByKey(Fields.tag))
                .anchor((String) record.getValueByKey(Fields.anchor))
                .signal((Double) record.getValueByKey("_value"))
                .build();
    }
}
