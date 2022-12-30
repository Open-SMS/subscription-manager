package uk.co.zodiac2000.subscriptionmanager.transfer.subscription;

import java.util.Optional;
import uk.co.zodiac2000.subscriptionmanager.constraint.ValidDateRange;
import uk.co.zodiac2000.subscriptionmanager.constraint.ValidDateString;

/**
 * Represents a new state for subscription start and end dates.
 */
@ValidDateRange(firstDatePropertyName = "startDate", secondDatePropertyName = "endDate")
public class SubscriptionDatesCommandDto {

    @ValidDateString
    private Optional<String> startDate;

    @ValidDateString
    private Optional<String> endDate;

    /**
     * Zero-arg constructor to allow ObjectMapper to create this class.
     */
    public SubscriptionDatesCommandDto() { }

    /**
     * Constructs a new SubscriptionDatesCommandDto using the supplied arguments.
     * Dates are represented as an string formatted as
     * {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE}, or an empty optional if not set.
     * @param startDate the date from which the subscription is active
     * @param endDate the date until which the subscription is active
     */
    public SubscriptionDatesCommandDto(Optional<String> startDate, Optional<String> endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
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
}
