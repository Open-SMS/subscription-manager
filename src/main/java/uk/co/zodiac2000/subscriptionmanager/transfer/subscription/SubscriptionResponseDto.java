package uk.co.zodiac2000.subscriptionmanager.transfer.subscription;

import java.time.LocalDate;
import java.util.Optional;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.SubscriptionContentResponseDto;

/**
 * Response DTO representing a subscription.
 */
public class SubscriptionResponseDto {

    private final Long id;

    private final Optional<LocalDate> startDate;

    private final Optional<LocalDate> endDate;

    private final boolean terminated;

    private final boolean suspended;

    private final SubscriptionContentResponseDto subscriptionContent;

    private final Long subscriberId;

    private final boolean active;

    private final boolean canBeSuspended;

    private final boolean canBeTerminated;

    private final boolean canBeUnsuspended;

    /**
     * Constructs a new SubscriptionResponseDto using the supplied arguments.
     * @param id the subscription identifier
     * @param startDate the subscription start date
     * @param endDate the subscription end date
     * @param terminated true if the subscription has been terminated
     * @param suspended true if the subscription is suspended
     * @param subscriptionContent the subscription content associated with this subscription
     * @param subscriberId the identifier of the subscriber that is the beneficiary of this subscription
     * @param active true if this subscription is active
     * @param canBeSuspended true if the subscription can be suspended
     * @param canBeTerminated true if the subscription can be terminated
     * @param canBeUnsuspended true if the subscription can be unsuspended
     */
    public SubscriptionResponseDto(final Long id, final Optional<LocalDate> startDate,
            final Optional<LocalDate> endDate, final boolean terminated, final boolean suspended,
            final SubscriptionContentResponseDto subscriptionContent, final Long subscriberId, final boolean active,
            final boolean canBeSuspended, final boolean canBeTerminated, final boolean canBeUnsuspended) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.terminated = terminated;
        this.suspended = suspended;
        this.subscriptionContent = subscriptionContent;
        this.subscriberId = subscriberId;
        this.active = active;
        this.canBeSuspended = canBeSuspended;
        this.canBeTerminated = canBeTerminated;
        this.canBeUnsuspended = canBeUnsuspended;
    }

    /**
     * @return the subscription identifier
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @return the subscription start date
     */
    public Optional<LocalDate> getStartDate() {
        return this.startDate;
    }

    /**
     * @return the subscription end date
     */
    public Optional<LocalDate> getEndDate() {
        return this.endDate;
    }

    /**
     * @return true if the subscription has been terminated
     */
    public boolean isTerminated() {
        return this.terminated;
    }

    /**
     * @return true if the subscription is suspended
     */
    public boolean isSuspended() {
        return this.suspended;
    }

    /**
     * @return the subscription content associated with this subscription
     */
    public SubscriptionContentResponseDto getSubscriptionContent() {
        return this.subscriptionContent;
    }

    /**
     * @return the identifier of the subscriber that is the beneficiary of this subscription
     */
    public Long getSubscriberId() {
        return this.subscriberId;
    }

    /**
     * @return true if this subscription is active
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * @return true if the subscription can be suspended
     */
    public boolean getCanBeSuspended() {
        return this.canBeSuspended;
    }

    /**
     * @return true if the subscription can be terminated
     */
    public boolean getCanBeTerminated() {
        return this.canBeTerminated;
    }


    /**
     * @return true if the subscription can be unsuspended
     */
    public boolean getCanBeUnsuspended() {
        return this.canBeUnsuspended;
    }
}
