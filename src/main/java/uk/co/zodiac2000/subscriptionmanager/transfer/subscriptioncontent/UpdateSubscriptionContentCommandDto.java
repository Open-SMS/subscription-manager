package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent;

import java.io.Serializable;
import java.util.List;

/**
 * Command DTO representing a change to a subscription content.
 */
public class UpdateSubscriptionContentCommandDto extends NewSubscriptionContentCommandDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id = null;

    /**
     * Zero-arg constructor to allow ObjectMapper to create this class.
     */
    public UpdateSubscriptionContentCommandDto() { }

    /**
     * Constructs a new UpdateSubscriptionContentCommandDto with the supplied arguments.
     * @param id the subscription content identifier
     * @param contentDescription description of the subscription content
     * @param contentIdentifiers the content identifiers associated with this subscription content
     * @param subscriptionResourceId the identifier of the subscription resource this subscription content is
     * associated with
     */
    public UpdateSubscriptionContentCommandDto(final long id, final String contentDescription,
            final List<ContentIdentifierCommandDto> contentIdentifiers, final String subscriptionResourceId) {
        super(contentDescription, contentIdentifiers, subscriptionResourceId);
        this.id = id;
    }

    /**
     * Sets the subscription content identifier.
     * @param id the subscription content identifier
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * @return the subscription content identifier
     */
    public Long getId() {
        return this.id;
    }
}
