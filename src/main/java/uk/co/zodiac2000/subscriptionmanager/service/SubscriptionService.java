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
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionContentIdCommandDto;
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
     * Updates the subscription content id for the Subscription identified by id. Returns a response DTO
     * representing the updated subscription. If the subscription doesn't exist then an empty optional is returned and
     * no change is made to the state of the system.
     * @param id the subscription identifier
     * @param commandDto command DTO representing the updated subscription content id
     * @return a SubscriptionResponseDto
     */
    @Transactional(readOnly = false)
    public Optional<SubscriptionResponseDto> updateSubscriptionContentId(final long id,
            final SubscriptionContentIdCommandDto commandDto) {
        Optional<Subscription> subscription = this.subscriptionRepository.findById(id);
        subscription.ifPresent(s -> s.setSubscriptionContentId(commandDto));
        return this.subscriptionResponseDtoFactory.subscriptionToSubscriptionResponseDto(subscription);
    }

    /**
     * Suspends the subscription identified by the {@code id} argument. Returns the state of the updated subscription
     * or an empty {@code Optional} if not found. Throws an IllegalStateException if the subscription cannot be
     * suspended. See
     * {@link uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription#suspend Subscription#suspend} for
     * details.
     * @param id the subscription identifier
     * @return the updated subscription
     * @throws IllegalStateException if the subscription cannot be suspended
     */
    @Transactional(readOnly = false)
    public Optional<SubscriptionResponseDto> suspendSubscription(final long id) {
        Optional<Subscription> subscription = this.subscriptionRepository.findById(id);
        subscription.ifPresent(s -> s.suspend());
        return this.subscriptionResponseDtoFactory.subscriptionToSubscriptionResponseDto(subscription);
    }

    /**
     * Terminates the subscription identified by the {@code id} argument. Returns the state of the updated subscription
     * or an empty {@code Optional} if not found. Throws an IllegalStateException if the subscription cannot be
     * terminated. See
     * {@link uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription#terminate Subscription#terminate}
     * for details.
     * @param id the subscription identifier
     * @return the updated subscription
     * @throws IllegalStateException if the subscription cannot be terminated
     */
    @Transactional(readOnly = false)
    public Optional<SubscriptionResponseDto> terminateSubscription(final long id) {
        Optional<Subscription> subscription = this.subscriptionRepository.findById(id);
        subscription.ifPresent(s -> s.terminate());
        return this.subscriptionResponseDtoFactory.subscriptionToSubscriptionResponseDto(subscription);
    }

    /**
     * Unsuspends the subscription identified by the {@code id} argument. Returns the state of the updated subscription
     * or an empty {@code Optional} if not found. Throws an IllegalStateException if the subscription cannot be
     * unsuspended. See
     * {@link uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription#terminate Subscription#unsuspend}
     * for details.
     * @param id the subscription identifier
     * @return the updated subscription
     * @throws IllegalStateException if the subscription cannot be unsuspended
     */
    @Transactional(readOnly = false)
    public Optional<SubscriptionResponseDto> unsuspendSubscription(final long id) {
        Optional<Subscription> subscription = this.subscriptionRepository.findById(id);
        subscription.ifPresent(s -> s.unsuspend());
        return this.subscriptionResponseDtoFactory.subscriptionToSubscriptionResponseDto(subscription);
    }
}
