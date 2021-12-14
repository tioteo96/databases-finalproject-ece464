package com.packet.indoor.service;

import java.util.List;
import java.util.stream.Collectors;

import com.packet.indoor.domain.tag.Tag;
import com.packet.indoor.domain.tag.dto.TagCreateRequestDto;
import com.packet.indoor.domain.tag.dto.TagResponseDto;
import com.packet.indoor.repository.tag.TagRepository;
import com.packet.indoor.util.TagStatus;

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

    public List<TagResponseDto> findAllTags(){
        List<Tag> tags = tagRepository.findAll();
        List<TagResponseDto> responseDtos = tags.stream()
                                            .map(tag -> tag.toResponseDto())
                                            .collect(Collectors.toList());
        return responseDtos;
    }

    public List<TagResponseDto> findAllAvailableTags(TagStatus tagStatus){
        List<Tag> available_tags = tagRepository.findAllByTagStatus(tagStatus);
        List<TagResponseDto> responseDtos = available_tags.stream()
                                            .map(tag -> tag.toResponseDto())
                                            .collect(Collectors.toList());
        return responseDtos;
    }
}
