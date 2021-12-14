package com.packet.indoor.domain.tag.dto;

import com.packet.indoor.domain.tag.Tag;
import lombok.Getter;

@Getter
public class TagCreateRequestDto {
    private String manufacturer;
    private String description;

    public Tag toEntity() {
        return Tag.create(this.manufacturer, this.description);
    }
}
