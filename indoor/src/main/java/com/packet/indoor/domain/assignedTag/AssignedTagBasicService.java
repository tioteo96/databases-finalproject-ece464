package com.packet.indoor.domain.assignedTag;

import com.packet.indoor.domain.user.User;
import com.packet.indoor.repository.assignedTag.AssignedTagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AssignedTagBasicService {

    private AssignedTagRepository assignedTagRepository;

    public boolean userHasAssignedTag(User user) {
        Optional<AssignedTag> assignedTagOptional = assignedTagRepository.findByUserAndAssignedIsTrue(user);
        return assignedTagOptional.isPresent();
    }
}
