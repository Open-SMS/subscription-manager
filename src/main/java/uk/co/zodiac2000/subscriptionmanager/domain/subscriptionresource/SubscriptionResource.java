package uk.co.zodiac2000.subscriptionmanager.domain.subscriptionresource;

import java.io.Serializable;
import java.net.URI;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import static uk.co.zodiac2000.subscriptionmanager.ApplicationConstants.MAX_RESOURCE_DESCRIPTION_LENGTH;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.UpdateSubscriptionResourceCommandDto;

/**
 * Class representing a subscribeable resource, for example a website that provides content available to subscribers.
 * The resource is uniquely identified by {@code resourceUri}. This may be a URL identifying a website or part of a
 * website, or any other URI that is used to identify a resource in an authorization request.
 */
@Entity
public class SubscriptionResource implements Serializable {

    private static final long serialVersionUID = 21412L;

    @Id
    @SequenceGenerator(name = "subscription_resource_id_gen", sequenceName = "subscription_resource_id_seq",
            allocationSize = 1)
    @GeneratedValue(generator = "subscription_resource_id_gen")
    private Long id;

    @NotNull
    private URI resourceUri;

    @NotEmpty
    @Length(max = MAX_RESOURCE_DESCRIPTION_LENGTH)
    private String resourceDescription;

    /**
     * Zero-arg constructor for JPA.
     */
    public SubscriptionResource() { }

    /**
     * Constructs a new SubscriptionResource using the supplied arguments.
     * @param resourceUri the resource URI
     * @param resourceDescription the resource description
     */
    public SubscriptionResource(final URI resourceUri, final String resourceDescription) {
        this.resourceUri = resourceUri;
        this.resourceDescription = resourceDescription;
    }

    /**
     * @return the resource id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @return the resource URI
     */
    public URI getResourceUri() {
        return this.resourceUri;
    }

    /**
     * @return the resource description
     */
    public String getResourceDescription() {
        return this.resourceDescription;
    }

    /**
     * Updates the resourceUri and resourceDescription properties of this subscription resource.
     * @param commandDto command DTO representing the updated fields
     */
    public void updateSubscriptionResource(final UpdateSubscriptionResourceCommandDto commandDto) {
        this.resourceUri = URI.create(commandDto.getResourceUri());
        this.resourceDescription = commandDto.getResourceDescription();
    }
}
