package com.packet.indoor.domain.assignedTag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class AssignedTagResponseDto {
    private String username;
    private String tagId;
    private String manufacturer;
    private String tag_description;
    private LocalDateTime assignedAt;
}
