package uk.co.zodiac2000.subscriptionmanager.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.zodiac2000.subscriptionmanager.domain.contentidentifier.ContentIdentifier;
import uk.co.zodiac2000.subscriptionmanager.factory.ContentIdentifierResponseDtoFactory;
import uk.co.zodiac2000.subscriptionmanager.repository.ContentIdentifierRepository;
import uk.co.zodiac2000.subscriptionmanager.transfer.contentidentifier.ContentIdentifierResponseDto;

/**
 * Service facade for ContentIdentifier aggregates.
 */
@Service
public class ContentIdentifierService {

    @Autowired
    private ContentIdentifierRepository contentIdentifierRepository;

    @Autowired
    private ContentIdentifierResponseDtoFactory contentIdentifierResponseDtoFactory;

    /**
     * Returns the content identifier identified by {@code id} or an empty optional if not found.
     * @param id the content identifier id
     * @return a ContentIdentifierResponseDto object
     */
    public Optional<ContentIdentifierResponseDto> getContentIdentifier(final long id) {
        Optional<ContentIdentifier> contentIdentifier = this.contentIdentifierRepository.findById(id);
        return this.contentIdentifierResponseDtoFactory.contentIdentifierToResponseDto(contentIdentifier);
    }

}
