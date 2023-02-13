package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent;

import java.util.Set;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceResponseDto;

/**
 * Response DTO representing subscription content.
 */
public class SubscriptionContentResponseDto {

    private final long id;

    private final String contentDescription;

    private final Set<String> contentIdentifiers;

    private final SubscriptionResourceResponseDto subscriptionResource;

    /**
     * Constructs a new SubscriptionContentResponseDto using the supplied arguments.
     * @param id the subscription content identifier
     * @param contentDescription description of this subscription content
     * @param contentIdentifiers the content identifiers associated with this subscription content
     * @param subscriptionResource the subscription resource associated with this subscription content
     */
    public SubscriptionContentResponseDto(final long id, final String contentDescription,
            final Set<String> contentIdentifiers,
            final SubscriptionResourceResponseDto subscriptionResource) {
        this.id = id;
        this.contentDescription = contentDescription;
        this.contentIdentifiers = contentIdentifiers;
        this.subscriptionResource = subscriptionResource;
    }

    /**
     * @return the subscription content identifier
     */
    public long getId() {
        return this.id;
    }

    /**
     * @return the content identifiers associated with this subscription content
     */
    public Set<String> getContentIdentifiers() {
        return this.contentIdentifiers;
    }

    /**
     * @return description of this subscription content
     */
    public String getContentDescription() {
        return this.contentDescription;
    }

    /**
     * @return the subscription resource associated with this subscription content
     */
    public SubscriptionResourceResponseDto getSubscriptionResource() {
        return this.subscriptionResource;
    }
}
