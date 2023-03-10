package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent;

import java.io.Serializable;
import java.util.List;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceResponseDto;

/**
 * Response DTO representing subscription content.
 */
public class SubscriptionContentResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private final long id;

    private final String contentDescription;

    private final List<String> contentIdentifiers;

    private final SubscriptionResourceResponseDto subscriptionResource;

    /**
     * Constructs a new SubscriptionContentResponseDto using the supplied arguments.
     * @param id the subscription content identifier
     * @param contentDescription description of this subscription content
     * @param contentIdentifiers the content identifiers associated with this subscription content
     * @param subscriptionResource the subscription resource associated with this subscription content
     */
    public SubscriptionContentResponseDto(final long id, final String contentDescription,
            final List<String> contentIdentifiers,
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
    public List<String> getContentIdentifiers() {
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
