package com.packet.indoor.service;

import com.packet.indoor.domain.tag.Tag;
import com.packet.indoor.domain.tag.dto.TagCreateRequestDto;
import com.packet.indoor.domain.tag.dto.TagResponseDto;
import com.packet.indoor.repository.tag.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TagService {

    private TagRepository tagRepository;

    public TagResponseDto registerNewTag(TagCreateRequestDto requestDto) {
        Tag newTag = requestDto.toEntity();
        tagRepository.save(newTag);
        TagResponseDto responseDto = newTag.toResponseDto();
        return responseDto;
    }
}
