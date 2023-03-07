package uk.co.zodiac2000.subscriptionmanager.transfer.subscription;

import javax.validation.GroupSequence;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import uk.co.zodiac2000.subscriptionmanager.ApplicationConstants;
import uk.co.zodiac2000.subscriptionmanager.constraint.DataConsistencyChecks;
import uk.co.zodiac2000.subscriptionmanager.constraint.DataFormatChecks;
import uk.co.zodiac2000.subscriptionmanager.constraint.Exists;

/**
 * Command DTO representing subscription content to be associated with a subscription.
 */
@GroupSequence({DataFormatChecks.class, DataConsistencyChecks.class, SubscriptionContentIdCommandDto.class})
public class SubscriptionContentIdCommandDto {

    @NotNull(groups = DataFormatChecks.class)
    @Digits(integer = ApplicationConstants.MAX_LONG_DIGITS, fraction = 0, groups = DataFormatChecks.class)
    @Exists(expression = "@subscriptionContentService.isPresent(#this)", groups = DataConsistencyChecks.class)
    private String subscriptionContentId;

    /**
     * Zero-arg constructor to allow ObjectMapper to create this class.
     */
    public SubscriptionContentIdCommandDto() { }

    /**
     * Constructs a new SubscriptionContentIdCommandDto using the supplied argument.
     * @param subscriptionContentId the identifier of the subscription content that this subscription
     * provides access to
     */
    public SubscriptionContentIdCommandDto(final String subscriptionContentId) {
        this.subscriptionContentId = subscriptionContentId;
    }

    /**
     * @return the identifier of the subscription content that this subscription provides access to
     */
    public String getSubscriptionContentId() {
        return this.subscriptionContentId;
    }
}
