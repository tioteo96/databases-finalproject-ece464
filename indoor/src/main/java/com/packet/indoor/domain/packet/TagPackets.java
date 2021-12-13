package com.packet.indoor.domain.packet;

import com.influxdb.query.FluxRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class TagPackets {
    private Instant time;
    private Map<String, Double> anchors;

    public static TagPackets create(FluxRecord record) {
        Set<String> keys = record.getValues().keySet();
        Map<String, Object> values = record.getValues();
        Map<String, Double> anchors = new HashMap<>();
        for (String key : keys) {
            if (key.equals("result") || key.equals("table") || key.equals("_time")) continue;
            anchors.put(key, (Double) values.get(key));
        }
        return TagPackets.builder()
                .time((Instant) values.get("_time"))
                .anchors(anchors)
                .build();
    }
}
