package com.packet.indoor.domain.anchor;

import com.packet.indoor.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Builder(builderClassName = "Builder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "anchor")
@Entity
public class Anchor extends BaseEntity{
    
    @EmbeddedId
    private AnchorId anchorId;

    @Column(nullable = false)
    private String secret;
    private String manufacturer;
    private String description;
    private Double xCoordinate;
    private Double yCoordinate;
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
