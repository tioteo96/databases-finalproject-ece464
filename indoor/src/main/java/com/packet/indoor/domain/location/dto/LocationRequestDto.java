package com.packet.indoor.domain.location.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class LocationRequestDto {
    private List<String> ids;
}
