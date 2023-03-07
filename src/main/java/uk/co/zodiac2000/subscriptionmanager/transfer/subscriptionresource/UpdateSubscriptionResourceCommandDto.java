package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource;

import java.io.Serializable;
import uk.co.zodiac2000.subscriptionmanager.constraint.DoesNotExist;

/**
 * Command DTO representing a change to a subscription resource.
 */
@DoesNotExist(expression = "@subscriptionResourceService.getSubscriptionResourceIdByUri(#this.resourceUri)",
        propertyName = "resourceUri")
public class UpdateSubscriptionResourceCommandDto extends NewSubscriptionResourceCommandDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id = null;

    /**
     * Zero-arg constructor to allow ObjectMapper to create this class.
     */
    public UpdateSubscriptionResourceCommandDto() { }

    /**
     * Constructs a new UpdateSubscriptionResourceCommandDto using the supplied arguments.
     * @param id the subscription resource identifier
     * @param resourceUri the subscription resource URI which uniquely identifies this subscription resource
     * @param resourceDescription the subscription resource description
     */
    public UpdateSubscriptionResourceCommandDto(final long id, final String resourceUri,
            final String resourceDescription) {
        super(resourceUri, resourceDescription);
        this.id = id;
    }

    /**
     * Sets the subscription resource identifier.
     * @param id the subscription resource identifier
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * @return the subscription resource identifier
     */
    public Long getId() {
        return this.id;
    }
}
