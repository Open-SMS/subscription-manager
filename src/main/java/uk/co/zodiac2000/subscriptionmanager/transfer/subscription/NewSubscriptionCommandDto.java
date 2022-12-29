package uk.co.zodiac2000.subscriptionmanager.transfer.subscription;

import java.util.Optional;
import uk.co.zodiac2000.subscriptionmanager.constraint.ValidDateString;

/**
 * Command DTO representing a new subscription. Dates are represented as an string formatted as
 * {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE}, or an empty string if not set.
 */
public class NewSubscriptionCommandDto {

    @ValidDateString
    private Optional<String> startDate;

    @ValidDateString
    private Optional<String> endDate;

    private String contentIdentifier;

    private String subscriberId;

    /**
     * Zero-arg constructor to allow ObjectMapper to create this class.
     */
    public NewSubscriptionCommandDto() { }

    /**
     * Constructs a new NewSubscriptionCommandDto using the supplied arguments.
     * @param startDate the date from which the subscription is active
     * @param endDate the date until which the subscription is active
     * @param contentIdentifier a string that describes the content that is the subject of the subscription
     * @param subscriberId the subscriber that is the beneficiary of this subscription
     */
    public NewSubscriptionCommandDto(Optional<String> startDate, Optional<String> endDate, String contentIdentifier,
            String subscriberId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.contentIdentifier = contentIdentifier;
        this.subscriberId = subscriberId;
    }

    /**
     * @return the date from which the subscription is active
     */
    public Optional<String> getStartDate() {
        return this.startDate;
    }

    /**
     * @return the date until which the subscription is active
     */
    public Optional<String> getEndDate() {
        return this.endDate;
    }

    /**
     * @return a string that describes the content that is the subject of the subscription
     */
    public String getContentIdentifier() {
        return this.contentIdentifier;
    }

    /**
     * @return the identifier of the subscriber that is the beneficiary of this subscription
     */
    public String getSubscriberId() {
        return this.subscriberId;
    }
}
