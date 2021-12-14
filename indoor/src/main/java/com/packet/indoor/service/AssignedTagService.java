package com.packet.indoor.service;

import com.packet.indoor.domain.assignedTag.AssignedTag;
import com.packet.indoor.domain.assignedTag.AssignedTagBasicService;
import com.packet.indoor.domain.assignedTag.dto.AssignedTagResponseDto;
import com.packet.indoor.domain.tag.Tag;
import com.packet.indoor.domain.tag.TagBasicService;
import com.packet.indoor.domain.user.User;
import com.packet.indoor.domain.user.UserBasicService;
import com.packet.indoor.exception.IllegalActionException;
import com.packet.indoor.repository.assignedTag.AssignedTagRepository;
import com.packet.indoor.util.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AssignedTagService {

    private UserBasicService userBasicService;
    private TagBasicService tagBasicService;
    private AssignedTagBasicService assignedTagBasicService;
    private AssignedTagRepository assignedTagRepository;

    @Transactional
    public AssignedTagResponseDto assignTagToUser(UUID tagId, String username) {
        User user = userBasicService.findUser(username);
        if (assignedTagBasicService.userHasAssignedTag(user)) throw new IllegalActionException(ErrorMessage.USER_ALREADY_ASSIGNED);
        Tag tag = tagBasicService.findAvailableTag(tagId);

        AssignedTag assignedTag = AssignedTag.create(user, tag, LocalDateTime.now());
        assignedTagRepository.save(assignedTag);
        tag.assign();

        AssignedTagResponseDto responseDto = assignedTag.toResponseDto();
        return responseDto;
    }

    @Transactional
    public AssignedTagResponseDto unAssignTag(String username) {
        User user = userBasicService.findUser(username);
        Optional<AssignedTag> assignedTagOptional = assignedTagRepository.findByUserAndAssignedIsTrue(user);
        if (assignedTagOptional.isEmpty()) throw new IllegalActionException(ErrorMessage.USER_NOT_ASSIGNED);
        AssignedTag assignedTag = assignedTagOptional.get();

        assignedTag.unAssign();
        AssignedTagResponseDto responseDto = assignedTag.toResponseDto();
        return responseDto;
    }
}
