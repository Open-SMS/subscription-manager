package uk.co.zodiac2000.subscriptionmanager.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription;
import uk.co.zodiac2000.subscriptionmanager.factory.SubscriptionFactory;
import uk.co.zodiac2000.subscriptionmanager.factory.SubscriptionResponseDtoFactory;
import uk.co.zodiac2000.subscriptionmanager.repository.SubscriptionRepository;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.NewSubscriptionCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionContentIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionDatesCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionResponseDto;

/**
 * Service facade for Subscription aggregates.
 */
@Service
@Transactional(readOnly = true)
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SubscriptionResponseDtoFactory subscriptionResponseDtoFactory;

    @Autowired
    private SubscriptionFactory subscriptionFactory;

    /**
     * Returns the subscription identified by id or an empty optional if not found.
     * @param id the subscription id
     * @return a response DTO representing the subscription
     */
    public Optional<SubscriptionResponseDto> getSubscription(final long id) {
        Optional<Subscription> subscription = this.subscriptionRepository.findById(id);
        return this.subscriptionResponseDtoFactory.subscriptionToSubscriptionResponseDto(subscription);
    }

    /**
     * Deletes the subscription identified by id.
     * @param id the subscription identifier
     */
    @Transactional(readOnly = false)
    public void deleteSubscription(final long id) {
        this.subscriptionRepository.findById(id).ifPresent(this.subscriptionRepository::delete);
    }

    /**
     * Creates a new Subscription aggregate root and persists it. Returns a response DTO representing the new
     * subscription.
     * @param commandDto command DTO representing the new subscription
     * @return a SubscriptionResponseDto
     */
    @Transactional(readOnly = false)
    public Optional<SubscriptionResponseDto> createSubscription(final NewSubscriptionCommandDto commandDto) {
        Subscription subscription = this.subscriptionRepository.save(
                this.subscriptionFactory.subscriptionCommandDtoToSubscription(commandDto)
        );

        return this.subscriptionResponseDtoFactory.subscriptionToSubscriptionResponseDto(Optional.of(subscription));
    }

    /**
     * Updates the start and end dates of the Subscription identified by id. Returns a response DTO representing the
     * updated subscription. If the subscription doesn't exist then an empty optional is returned and no change is
     * made to the state of the system.
     * @param id the subscription identifier
     * @param commandDto command DTO representing the new subscription dates
     * @return a SubscriptionResponseDto
     */
    @Transactional(readOnly = false)
    public Optional<SubscriptionResponseDto> updateSubscriptionDates(final long id,
            final SubscriptionDatesCommandDto commandDto) {
        Optional<Subscription> subscription = this.subscriptionRepository.findById(id);
        subscription.ifPresent(s -> s.setDates(commandDto));
        return this.subscriptionResponseDtoFactory.subscriptionToSubscriptionResponseDto(subscription);
    }

    /**
     * Updates the content identifier associated with the Subscription identified by id. Returns a response DTO
     * representing the updated subscription. If the subscription doesn't exist then an empty optional is returned and
     * no change is made to the state of the system.
     * @param id the subscription identifier
     * @param commandDto command DTO representing the updated content identifier
     * @return a SubscriptionResponseDto
     */
    @Transactional(readOnly = false)
    public Optional<SubscriptionResponseDto> updateSubscriptionContentIdentifier(final long id,
            final SubscriptionContentIdentifierCommandDto commandDto) {
        Optional<Subscription> subscription = this.subscriptionRepository.findById(id);
        subscription.ifPresent(s -> s.setContentIdentifier(commandDto));
        return this.subscriptionResponseDtoFactory.subscriptionToSubscriptionResponseDto(subscription);
    }
}
