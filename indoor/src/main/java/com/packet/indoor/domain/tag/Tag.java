package com.packet.indoor.domain.tag;

import com.packet.indoor.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;


@Builder(builderClassName = "Builder")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tag")
@Entity
public class Tag extends BaseEntity{
    
    @EmbeddedId
    private TagId tagId;
    @Embedded
    private String manufacturer;
    @Embedded
    private String description;
    @Embedded
    @NonNull
    private Boolean use_status;

    public static Tag create(String manufacturer, String description, Boolean use_status) {
        return Tag.builder()
                .manufacturer(manufacturer)
                .description(description)
                .use_status(use_status)
                .build();
    }

}
