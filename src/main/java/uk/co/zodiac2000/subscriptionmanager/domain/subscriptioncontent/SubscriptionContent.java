package uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 * Class representing the content that a subscription provides access to. The scope of subscription content is defined
 * by the subscription resource referenced by {@code subscriptionResourceId}.
 */
@Entity
public class SubscriptionContent implements Serializable {

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
    public SubscriptionContent() { }

    /**
     * Constructs a new SubscriptionContent using the supplied arguments.
     * @param subscriptionResourceId the subscription resource the defines the scope of this subscription content
     */
    public SubscriptionContent(final Long subscriptionResourceId) {
        this.subscriptionResourceId = subscriptionResourceId;
    }

    /**
     * @return the subscription content id
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