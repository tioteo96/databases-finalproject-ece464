package com.packet.indoor.service;

import com.packet.indoor.domain.anchor.Anchor;
import com.packet.indoor.domain.anchor.dto.AnchorRequestDto;
import com.packet.indoor.domain.anchor.dto.AnchorResponseDto;
import com.packet.indoor.repository.anchor.AnchorRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AnchorService {

    private AnchorRepository anchorRepository;

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public AnchorResponseDto registerNewAnchor(AnchorRequestDto requestDto) {
        Anchor newAnchor = requestDto.toEntity();
        anchorRepository.save(newAnchor);
        AnchorResponseDto responseDto = newAnchor.toResponseDto();
        return responseDto;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<AnchorResponseDto> findAllAnchorsOfManufacturer(String manufacturer) {
        List<Anchor> anchors = anchorRepository.findAllByManufacturerIgnoreCase(manufacturer);
        List<AnchorResponseDto> responseDtos = anchors.stream()
                .map(anchor -> anchor.toResponseDto())
                .collect(Collectors.toList());
        return responseDtos;
    }
}
