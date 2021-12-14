package com.packet.indoor.domain.tag;

import com.packet.indoor.exception.NotFoundException;
import com.packet.indoor.repository.tag.TagRepository;
import com.packet.indoor.util.ErrorMessage;
import com.packet.indoor.util.TagStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class TagBasicService {

    private TagRepository tagRepository;

    public Tag findAvailableTag(UUID tagId) {
        Optional<Tag> tagOptional = tagRepository.findByIdAndTagStatus(tagId, TagStatus.AVAILABLE);
        if (tagOptional.isEmpty()) throw new NotFoundException(ErrorMessage.TAG_NOT_FOUND);
        return tagOptional.get();
    }

}
