package com.packet.indoor.service;

import com.packet.indoor.domain.assignedTag.AssignedTag;
import com.packet.indoor.domain.assignedTag.dto.AssignedTagResponseDto;
import com.packet.indoor.domain.tag.Tag;
import com.packet.indoor.domain.tag.TagBasicService;
import com.packet.indoor.domain.user.User;
import com.packet.indoor.domain.user.UserBasicService;
import com.packet.indoor.repository.assignedTag.AssignedTagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AssignedTagService {

    private UserBasicService userBasicService;
    private TagBasicService tagBasicService;
    private AssignedTagRepository assignedTagRepository;

    @Transactional
    public AssignedTagResponseDto assignTagToUser(UUID tagId, String username) {
        User user = userBasicService.findUser(username);
        Tag tag = tagBasicService.findAvailableTag(tagId);

        AssignedTag assignedTag = AssignedTag.create(user, tag, LocalDateTime.now());
        assignedTagRepository.save(assignedTag);
        tag.assign();

        AssignedTagResponseDto responseDto = assignedTag.toResponseDto();
        return responseDto;
    }
}
