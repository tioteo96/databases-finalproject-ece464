package com.packet.indoor.domain.anchor;

import com.packet.indoor.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder(builderClassName = "Builder")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "anchor")
@Entity
public class Anchor extends BaseEntity{
    
    @EmbeddedId
    private AnchorId anchorId;
    @Embedded
    private String secret;
    @Embedded
    private String manufacturer;
    @Embedded
    private String description;
    @Embedded
    private Double xCoordinate;
    @Embedded
    private Double yCoordinate;
    @Embedded
    private Double zCoordinate;

    public static Anchor create(String manufacturer, String description, Double xCoordinate, Double yCoordinate, Double zCoordinate) {
        return Anchor.builder()
                .manufacturer(manufacturer)
                .description(description)
                .xCoordinate(xCoordinate)
                .yCoordinate(yCoordinate)
                .zCoordinate(zCoordinate)
                .build();
    }

}
