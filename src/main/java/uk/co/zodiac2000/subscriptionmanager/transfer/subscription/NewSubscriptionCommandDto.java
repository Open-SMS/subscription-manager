package uk.co.zodiac2000.subscriptionmanager.transfer.subscription;

import java.io.Serializable;
import java.util.Optional;
import javax.validation.GroupSequence;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import uk.co.zodiac2000.subscriptionmanager.ApplicationConstants;
import uk.co.zodiac2000.subscriptionmanager.constraint.DataConsistencyChecks;
import uk.co.zodiac2000.subscriptionmanager.constraint.DataFormatChecks;
import uk.co.zodiac2000.subscriptionmanager.constraint.Exists;
import uk.co.zodiac2000.subscriptionmanager.constraint.ValidDateRange;
import uk.co.zodiac2000.subscriptionmanager.constraint.ValidDateString;

/**
 * Command DTO representing a new subscription. Dates are represented as an string formatted as
 * {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE}, or an empty string if not set.
 */
@GroupSequence({DataFormatChecks.class, DataConsistencyChecks.class, NewSubscriptionCommandDto.class})
@ValidDateRange(firstDatePropertyName = "startDate", secondDatePropertyName = "endDate",
        groups = DataConsistencyChecks.class)
public class NewSubscriptionCommandDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ValidDateString(groups = DataFormatChecks.class)
    private Optional<String> startDate;

    @ValidDateString(groups = DataFormatChecks.class)
    private Optional<String> endDate;

    @NotNull(groups = DataFormatChecks.class)
    @Digits(integer = ApplicationConstants.MAX_LONG_DIGITS, fraction = 0, groups = DataFormatChecks.class)
    @Exists(expression = "@subscriptionContentService.isPresent(#this)", groups = DataConsistencyChecks.class)
    private String subscriptionContentId;

    @NotNull
    @Digits(integer = ApplicationConstants.MAX_LONG_DIGITS, fraction = 0, groups = DataFormatChecks.class)
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
