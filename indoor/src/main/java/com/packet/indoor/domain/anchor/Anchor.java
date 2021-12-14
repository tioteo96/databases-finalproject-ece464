package com.packet.indoor.domain.anchor;

import com.packet.indoor.domain.BaseEntity;
import com.packet.indoor.domain.anchor.dto.AnchorResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Builder(builderClassName = "Builder")
@Getter
@AllArgsConstructor
@Table(name = "anchor")
@Entity
public class Anchor extends BaseEntity{
    
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID secret;

    @Column(length = 100)
    private String manufacturer;
    @Column(length = 255)
    private String description;

    @Column(name = "x_cord", nullable = false)
    private Double xCoordinate;
    @Column(name = "y_cord", nullable = false)
    private Double yCoordinate;
    @Column(name = "z_cord")
    private Double zCoordinate;

    protected Anchor(){}

    public static Anchor create(String manufacturer, String description, Double xCoordinate, Double yCoordinate, Double zCoordinate) {
        return Anchor.builder()
                .id(UUID.randomUUID())
                .secret(UUID.randomUUID())
                .manufacturer(manufacturer)
                .description(description)
                .xCoordinate(xCoordinate)
                .yCoordinate(yCoordinate)
                .zCoordinate(zCoordinate)
                .build();
    }

    public AnchorResponseDto toResponseDto(){
        return AnchorResponseDto.builder()
                .secret(this.secret.toString())
                .manufacturer(this.manufacturer)
                .description(this.description)
                .xCoordinate(this.xCoordinate)
                .yCoordinate(this.yCoordinate)
                .zCoordinate(this.zCoordinate)
                .build();
    }

}
