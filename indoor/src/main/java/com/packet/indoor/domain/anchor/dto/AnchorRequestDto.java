package com.packet.indoor.domain.anchor.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.packet.indoor.domain.anchor.Anchor;
import com.packet.indoor.util.AnchorRequestDtoSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonDeserialize(using = AnchorRequestDtoSerializer.class)
@AllArgsConstructor
@Setter
@Getter
public class AnchorRequestDto {
    private String manufacturer;
    private String description;
    private Double xCoordinate;
    private Double yCoordinate;
    private Double zCoordinate;

    public Anchor toEntity(){
        return Anchor.create(this.manufacturer, this.description, this.xCoordinate, this.yCoordinate, this.zCoordinate);
    }
}
