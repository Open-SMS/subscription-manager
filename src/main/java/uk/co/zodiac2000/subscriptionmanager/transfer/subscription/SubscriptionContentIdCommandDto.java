package uk.co.zodiac2000.subscriptionmanager.transfer.subscription;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import uk.co.zodiac2000.subscriptionmanager.ApplicationConstants;
import uk.co.zodiac2000.subscriptionmanager.constraint.Exists;

/**
 * Command DTO representing subscription content to be associated with a subscription.
 */
public class SubscriptionContentIdCommandDto {

    @NotNull
    @Digits(integer = ApplicationConstants.MAX_LONG_DIGITS, fraction = 0)
    @Exists(expression = "@subscriptionContentService.isPresent(#this)")
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
