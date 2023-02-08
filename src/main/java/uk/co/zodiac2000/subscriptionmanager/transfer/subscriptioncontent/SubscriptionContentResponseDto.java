package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent;

import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceResponseDto;

/**
 * Response DTO representing subscription content.
 */
public class SubscriptionContentResponseDto {

    private final long id;

    private final SubscriptionResourceResponseDto subscriptionResource;

    /**
     * Constructs a new SubscriptionContentResponseDto using the supplied arguments.
     * @param id the subscription content identifier
     * @param subscriptionResource the subscription resource associated with this subscription content
     */
    public SubscriptionContentResponseDto(final long id, final SubscriptionResourceResponseDto subscriptionResource) {
        this.id = id;
        this.subscriptionResource = subscriptionResource;
    }

    /**
     * @return the subscription content identifier
     */
    public long getId() {
        return this.id;
    }

    /**
     * @return the subscription resource associated with this subscription content
     */
    public SubscriptionResourceResponseDto getSubscriptionResource() {
        return this.subscriptionResource;
    }
}
