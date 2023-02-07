package uk.co.zodiac2000.subscriptionmanager.transfer.contentidentifier;

import uk.co.zodiac2000.subscriptionmanager.transfer.resource.SubscriptionResourceResponseDto;

/**
 * Response DTO representing a content identifier.
 */
public class ContentIdentifierResponseDto {

    private final long id;

    private final SubscriptionResourceResponseDto subscriptionResource;

    /**
     * Constructs a new ContentIdentifierResponseDto using the supplied arguments.
     * @param id the content identifier id
     * @param subscriptionResource the subscription resource associated with this content identifier
     */
    public ContentIdentifierResponseDto(long id, SubscriptionResourceResponseDto subscriptionResource) {
        this.id = id;
        this.subscriptionResource = subscriptionResource;
    }

    /**
     * @return the content identifier id
     */
    public long getId() {
        return this.id;
    }

    /**
     * @return the subscription resource associated with this content identifier
     */
    public SubscriptionResourceResponseDto getSubscriptionResource() {
        return this.subscriptionResource;
    }
}
