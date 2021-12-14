package com.packet.indoor.domain.tag.dto;

import com.packet.indoor.util.TagStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class TagResponseDto {
    private String id;
    private String manufacturer;
    private String description;
    private TagStatus tagStatus;
}
