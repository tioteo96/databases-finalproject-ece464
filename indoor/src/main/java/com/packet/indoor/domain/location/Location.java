package com.packet.indoor.domain.location;

import com.influxdb.query.FluxRecord;
import com.packet.indoor.domain.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class Location {
    private String time;
    private String x;
    private String y;
    private String z;
    private String username;
    private String visitorType;

    public static Location create(FluxRecord fluxRecord, Visitor visitor) {
        Instant time = (Instant) fluxRecord.getValueByKey("_time");
        Double x = (Double) fluxRecord.getValueByKey("x");
        Double y = (Double) fluxRecord.getValueByKey("y");
        Double z = (Double) fluxRecord.getValueByKey("z");

        return Location.builder()
                .time(time.toString())
                .x(x.toString())
                .y(y.toString())
                .z(z.toString())
                .username(visitor.getUsername())
                .visitorType(visitor.getType().value())
                .build();
    }
}
