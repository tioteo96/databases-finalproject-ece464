package com.packet.indoor.service;

import com.packet.indoor.domain.anchor.Anchor;
import com.packet.indoor.domain.anchor.dto.AnchorRequestDto;
import com.packet.indoor.domain.anchor.dto.AnchorResponseDto;
import com.packet.indoor.repository.anchor.AnchorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AnchorService {

    private AnchorRepository anchorRepository;

    @Transactional
    public AnchorResponseDto registerNewAnchor(AnchorRequestDto requestDto) {
        Anchor newAnchor = requestDto.toEntity();
        anchorRepository.save(newAnchor);
        AnchorResponseDto responseDto = newAnchor.toResponseDto();
        return responseDto;
    }
}
