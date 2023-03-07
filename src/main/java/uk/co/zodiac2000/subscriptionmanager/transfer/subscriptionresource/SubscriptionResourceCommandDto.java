package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import static uk.co.zodiac2000.subscriptionmanager.ApplicationConstants.MAX_RESOURCE_DESCRIPTION_LENGTH;
import static uk.co.zodiac2000.subscriptionmanager.ApplicationConstants.MAX_RESOURCE_URI_LENGTH;
import uk.co.zodiac2000.subscriptionmanager.constraint.DoesNotExist;
import uk.co.zodiac2000.subscriptionmanager.constraint.ValidUriString;

/**
 * Command DTO representing a change to a subscription resource.
 */
@DoesNotExist(expression = "@subscriptionResourceService.getSubscriptionResourceIdByUri(#this.resourceUri)",
        propertyName = "resourceUri")
public class SubscriptionResourceCommandDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id = null;

    @ValidUriString
    @NotEmpty
    @Length(max = MAX_RESOURCE_URI_LENGTH)
    private String resourceUri;

    @NotEmpty
    @Length(max = MAX_RESOURCE_DESCRIPTION_LENGTH)
    private String resourceDescription;

    /**
     * Zero-arg constructor to allow ObjectMapper to create this class.
     */
    public SubscriptionResourceCommandDto() { }

    /**
     * Constructs a new SubscriptionResourceCommandDto using the supplied arguments.
     * @param id the subscription resource identifier
     * @param resourceUri the subscription resource URI which uniquely identifies this subscription resource
     * @param resourceDescription the subscription resource description
     */
    public SubscriptionResourceCommandDto(final long id, final String resourceUri, final String resourceDescription) {
        this.id = id;
        this.resourceUri = resourceUri;
        this.resourceDescription = resourceDescription;
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

    /**
     * @return the subscription resource URI which uniquely identifies this subscription resource
     */
    public String getResourceUri() {
        return this.resourceUri;
    }

    /**
     * @return the subscription resource description
     */
    public String getResourceDescription() {
        return this.resourceDescription;
    }

}
