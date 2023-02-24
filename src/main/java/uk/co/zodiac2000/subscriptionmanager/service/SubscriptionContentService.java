package uk.co.zodiac2000.subscriptionmanager.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent.SubscriptionContent;
import uk.co.zodiac2000.subscriptionmanager.factory.SubscriptionContentFactory;
import uk.co.zodiac2000.subscriptionmanager.factory.SubscriptionContentResponseDtoFactory;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.SubscriptionContentResponseDto;
import uk.co.zodiac2000.subscriptionmanager.repository.SubscriptionContentRepository;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.NewSubscriptionContentCommandDto;

/**
 * Service facade for SubscriptionContent aggregates.
 */
@Service
@Transactional(readOnly = true)
public class SubscriptionContentService {

    @Autowired
    private SubscriptionContentRepository subscriptionContentRepository;

    @Autowired
    private SubscriptionContentResponseDtoFactory subscriptionContentResponseDtoFactory;

    @Autowired
    private SubscriptionContentFactory subscriptionContentFactory;

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

    /**
     * Creates a new subscription content aggregate and persists it. Returns a response DTO representing the new
     * subscription content.
     * @param commandDto command DTO representing the state of the new subscription content
     * @return a SubscriptionContentResponseDto representing the new object
     */
    @Transactional(readOnly = false)
    public Optional<SubscriptionContentResponseDto> createSubscriptionContent(
            final NewSubscriptionContentCommandDto commandDto) {
        SubscriptionContent subscriptionContent = this.subscriptionContentRepository.save(
                this.subscriptionContentFactory.newSubscriptionContentCommandDtoToSubscriptionContent(commandDto));
        return this.subscriptionContentResponseDtoFactory.subscriptionContentToResponseDto(
                Optional.of(subscriptionContent));
    }
}
