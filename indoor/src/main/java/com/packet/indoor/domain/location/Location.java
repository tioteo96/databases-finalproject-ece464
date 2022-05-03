package com.packet.indoor.domain.location;

import com.influxdb.query.FluxRecord;
import com.packet.indoor.domain.board.Board;
import com.packet.indoor.domain.visitor.Visitor;
import com.packet.indoor.domain.visitor.VisitorType;
import com.packet.indoor.util.DurationUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class Location {
    private LocalDateTime time;
    private String x;
    private String y;
    private String z;
    private String boardName;
    private String username;
    private VisitorType visitorType;

    public static Location create(FluxRecord fluxRecord, Visitor visitor, Board board) {
        Instant time = (Instant) fluxRecord.getValueByKey("_time");
        Double x = (Double) fluxRecord.getValueByKey("x");
        Double y = (Double) fluxRecord.getValueByKey("y");
        Double z = (Double) fluxRecord.getValueByKey("z");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(time, ZoneId.systemDefault());

        return Location.builder()
                .time(localDateTime.truncatedTo(DurationUnit.ofMillis(500)))
                .x(x.toString())
                .y(y.toString())
                .z(z.toString())
                .boardName(board.getBoardName())
                .username(visitor.getUsername())
                .visitorType(visitor.getType())
                .build();
    }

    public String getName() {
        return this.visitorType.value() + "|" + this.boardName;
    }
}
