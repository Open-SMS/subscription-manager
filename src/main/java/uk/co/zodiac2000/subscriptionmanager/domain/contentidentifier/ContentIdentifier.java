package uk.co.zodiac2000.subscriptionmanager.domain.contentidentifier;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 * Class representing the content that a subscription provides access to. The scope of a content identifier is defined
 * by the subscription resource indicated by {@code subscriptionResourceId}.
 */
@Entity
public class ContentIdentifier implements Serializable {

    private static final long serialVersionUID = 31321L;

    @Id
    @SequenceGenerator(name = "content_identifier_id_gen", sequenceName = "content_identifier_id_seq",
            allocationSize = 1)
    @GeneratedValue(generator = "content_identifier_id_gen")
    private Long id;

    @NotNull
    private Long subscriptionResourceId;

    /**
     * Zero-arg constructor for JPA.
     */
    public ContentIdentifier() { }

    /**
     * Constructs a new ContentIdentifier using the supplied arguments.
     * @param subscriptionResourceId the subscription resource the defines the scope of this content identifier
     */
    public ContentIdentifier(Long subscriptionResourceId) {
        this.subscriptionResourceId = subscriptionResourceId;
    }

    /**
     * @return the content identifier id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @return the subscription resource id
     */
    public Long getSubscriptionResourceId() {
        return this.subscriptionResourceId;
    }
}
