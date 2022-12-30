package uk.co.zodiac2000.subscriptionmanager.transfer.subscription;

import javax.validation.constraints.NotEmpty;

/**
 * Command DTO representing a subscription content identifier.
 */
public class SubscriptionContentIdentifierCommandDto {

    @NotEmpty
    private String contentIdentifier;

    /**
     * Zero-arg constructor to allow ObjectMapper to create this class.
     */
    public SubscriptionContentIdentifierCommandDto() { }

    /**
     * Constructs a new SubscriptionContentIdentifierCommandDto using the supplied argument.
     * @param contentIdentifier a string that describes the content that is the subject of the subscription
     */
    public SubscriptionContentIdentifierCommandDto(String contentIdentifier) {
        this.contentIdentifier = contentIdentifier;
    }

    /**
     * @return a string that describes the content that is the subject of the subscription
     */
    public String getContentIdentifier() {
        return this.contentIdentifier;
    }
}
