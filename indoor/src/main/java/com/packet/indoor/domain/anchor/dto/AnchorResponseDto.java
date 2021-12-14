package com.packet.indoor.domain.anchor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class AnchorResponseDto {
    private String id;
    private String secret;
    private String manufacturer;
    private String description;
    private Double xCoordinate;
    private Double yCoordinate;
    private Double zCoordinate;

    protected AnchorResponseDto(){}
}
