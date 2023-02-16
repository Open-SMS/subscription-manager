package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import static uk.co.zodiac2000.subscriptionmanager.ApplicationConstants.MAX_RESOURCE_DESCRIPTION_LENGTH;
import static uk.co.zodiac2000.subscriptionmanager.ApplicationConstants.MAX_RESOURCE_URI_LENGTH;
import uk.co.zodiac2000.subscriptionmanager.constraint.DoesNotExist;
import uk.co.zodiac2000.subscriptionmanager.constraint.ValidUriString;

/**
 * Command DTO representing a new subscription resource.
 */
@DoesNotExist(expression = "@subscriptionResourceService.getSubscriptionResourceByUri(#this.resourceUri)",
        propertyName = "resourceUri")
public class NewSubscriptionResourceCommandDto {

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
    public NewSubscriptionResourceCommandDto() { }

    /**
     * Constructs a new NewSubscriptionResourceCommandDto using the supplied arguments.
     * @param resourceUri the subscription resource URI which uniquely identifies this subscription resource
     * @param resourceDescription the subscription resource description
     */
    public NewSubscriptionResourceCommandDto(final String resourceUri, final String resourceDescription) {
        this.resourceUri = resourceUri;
        this.resourceDescription = resourceDescription;
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
