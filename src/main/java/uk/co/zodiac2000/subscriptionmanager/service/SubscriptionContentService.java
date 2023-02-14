package uk.co.zodiac2000.subscriptionmanager.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent.SubscriptionContent;
import uk.co.zodiac2000.subscriptionmanager.factory.SubscriptionContentResponseDtoFactory;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.SubscriptionContentResponseDto;
import uk.co.zodiac2000.subscriptionmanager.repository.SubscriptionContentRepository;

/**
 * Service facade for SubscriptionContent aggregates.
 */
@Service
public class SubscriptionContentService {

    @Autowired
    private SubscriptionContentRepository subscriptionContentRepository;

    @Autowired
    private SubscriptionContentResponseDtoFactory subscriptionContentResponseDtoFactory;

    /**
     * Returns the subscription content identified by {@code id} or an empty optional if not found.
     * @param id the subscription content id
     * @return a SubscriptionContentResponseDto object
     */
    public Optional<SubscriptionContentResponseDto> getSubscriptionContent(final long id) {
        Optional<SubscriptionContent> contentIdentifier = this.subscriptionContentRepository.findById(id);
        return this.subscriptionContentResponseDtoFactory.subscriptionContentToResponseDto(contentIdentifier);
    }

    /**
     * Returns true if the SubscriptionContent object identified by id exists in the system.
     * @param id the subscription content identifier
     * @return true if the object exists
     */
    public boolean isPresent(final long id) {
        return this.subscriptionContentRepository.existsById(id);
    }
}
