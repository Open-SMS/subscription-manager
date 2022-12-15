package uk.co.zodiac2000.subscriptionmanager.transfer.subscription;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Response DTO representing a subscription.
 */
public class SubscriptionResponseDto {

    private final Long id;

    private final Optional<LocalDate> startDate;

    private final Optional<LocalDate> endDate;

    private final boolean terminated;

    private final String contentIdentifier;

    private final Long subscriberId;

    private final boolean active;

    /**
     * Constructs a new SubscriptionResponseDto using the supplied arguments.
     * @param id the subscription identifier
     * @param startDate the subscription start date
     * @param endDate the subscription end date
     * @param terminated true if the subscription has been terminated
     * @param contentIdentifier a string that describes the content that is the subject of the subscription
     * @param subscriberId the identifier of the subscriber that is the beneficiary of this subscription
     * @param active true if this subscription is active
     */
    public SubscriptionResponseDto(Long id, Optional<LocalDate> startDate, Optional<LocalDate> endDate, boolean terminated,
            String contentIdentifier, Long subscriberId, boolean active) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.terminated = terminated;
        this.contentIdentifier = contentIdentifier;
        this.subscriberId = subscriberId;
        this.active = active;
    }

    /**
     * @return the subscription identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the subscription start date
     */
    public Optional<LocalDate> getStartDate() {
        return startDate;
    }

    /**
     * @return the subscription end date
     */
    public Optional<LocalDate> getEndDate() {
        return endDate;
    }

    /**
     * @return true if the subscription has been terminated
     */
    public boolean isTerminated() {
        return terminated;
    }

    /**
     * @return a string that describes the content that is the subject of the subscription
     */
    public String getContentIdentifier() {
        return contentIdentifier;
    }

    /**
     * @return the identifier of the subscriber that is the beneficiary of this subscription
     */
    public Long getSubscriberId() {
        return subscriberId;
    }

    /**
     * @return true if this subscription is active
     */
    public boolean isActive() {
        return active;
    }
}
