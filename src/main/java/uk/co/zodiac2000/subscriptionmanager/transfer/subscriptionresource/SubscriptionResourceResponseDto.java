package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource;

/**
 * Response DTO representing a subscription resource.
 */
public class SubscriptionResourceResponseDto {

    private final long id;

    private final String resourceUri;

    private final String resourceDescription;

    /**
     * Constructs a new SubscriptionResourceResponseDto with the supplied arguments.
     * @param id the subscription resource id
     * @param resourceUri the resource URI
     * @param resourceDescription the resource description
     */
    public SubscriptionResourceResponseDto(final long id, final String resourceUri, final String resourceDescription) {
        this.id = id;
        this.resourceUri = resourceUri;
        this.resourceDescription = resourceDescription;
    }

    /**
     * @return the subscription resource id
     */
    public long getId() {
        return this.id;
    }

    /**
     * @return the resource URI as a string
     */
    public String getResourceUri() {
        return this.resourceUri;
    }

    /**
     * @return the resource description
     */
    public String getResourceDescription() {
        return this.resourceDescription;
    }
}
