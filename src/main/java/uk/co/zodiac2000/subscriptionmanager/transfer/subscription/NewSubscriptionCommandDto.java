package uk.co.zodiac2000.subscriptionmanager.transfer.subscription;

import java.util.Optional;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import uk.co.zodiac2000.subscriptionmanager.ApplicationConstants;
import uk.co.zodiac2000.subscriptionmanager.constraint.ValidDateRange;
import uk.co.zodiac2000.subscriptionmanager.constraint.ValidDateString;

/**
 * Command DTO representing a new subscription. Dates are represented as an string formatted as
 * {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE}, or an empty string if not set.
 */
@ValidDateRange(firstDatePropertyName = "startDate", secondDatePropertyName = "endDate")
public class NewSubscriptionCommandDto {

    @ValidDateString
    private Optional<String> startDate;

    @ValidDateString
    private Optional<String> endDate;

    @NotNull
    @Digits(integer = ApplicationConstants.MAX_LONG_DIGITS, fraction = 0)
    private String subscriptionContentId;

    @NotNull
    @Digits(integer = ApplicationConstants.MAX_LONG_DIGITS, fraction = 0)
    private String subscriberId;

    /**
     * Zero-arg constructor to allow ObjectMapper to create this class.
     */
    public NewSubscriptionCommandDto() { }

    /**
     * Constructs a new NewSubscriptionCommandDto using the supplied arguments.
     * @param startDate the date from which the subscription is active
     * @param endDate the date until which the subscription is active
     * @param subscriptionContentId the identifier of the subscription content that this subscription provides access to
     * @param subscriberId the subscriber that is the beneficiary of this subscription
     */
    public NewSubscriptionCommandDto(final Optional<String> startDate, final Optional<String> endDate,
            final String subscriptionContentId, final String subscriberId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.subscriptionContentId = subscriptionContentId;
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
     * @return the identifier of the subscription content that this subscription provides access to
     */
    public String getSubscriptionContentId() {
        return this.subscriptionContentId;
    }

    /**
     * @return the identifier of the subscriber that is the beneficiary of this subscription
     */
    public String getSubscriberId() {
        return this.subscriberId;
    }
}
