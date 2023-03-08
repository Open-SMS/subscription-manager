package uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import static uk.co.zodiac2000.subscriptionmanager.ApplicationConstants.MAX_LENGTH_CONTENT_DESCRIPTION;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.UpdateSubscriptionContentCommandDto;

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

    @NotEmpty
    @Size(max = MAX_LENGTH_CONTENT_DESCRIPTION)
    private String contentDescription;

    @Valid
    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "content_identifier")
    private Set<ContentIdentifier> contentIdentifiers;

    @NotNull
    private Long subscriptionResourceId;

    /**
     * Zero-arg constructor for JPA.
     */
    public SubscriptionContent() { }

    /**
     * Constructs a new SubscriptionContent using the supplied arguments.
     * @param contentDescription description of this subscription content
     * @param contentIdentifiers the content identifiers associated with this subscription content
     * @param subscriptionResourceId the subscription resource the defines the scope of this subscription content
     */
    public SubscriptionContent(final String contentDescription, final Set<ContentIdentifier> contentIdentifiers,
            final Long subscriptionResourceId) {
        this.contentDescription = Objects.requireNonNull(contentDescription);
        this.contentIdentifiers = Objects.requireNonNull(contentIdentifiers);
        this.subscriptionResourceId = Objects.requireNonNull(subscriptionResourceId);
    }

    /**
     * @return the subscription content id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @return description of this subscription content
     */
    public String getContentDescription() {
        return this.contentDescription;
    }

    /**
     * @return the content identifiers associated with this subscription content
     */
    public Set<ContentIdentifier> getContentIdentifiers() {
        return Set.copyOf(this.contentIdentifiers);
    }

    /**
     * @return the subscription resource id
     */
    public Long getSubscriptionResourceId() {
        return this.subscriptionResourceId;
    }

    /**
     * Updates the content description, content identifiers and subscription resource associated with this
     * subscription content.
     * @param commandDto command DTO representing the updated fields
     */
    public void updateSubscriptionContent(final UpdateSubscriptionContentCommandDto commandDto) {
        this.contentDescription = commandDto.getContentDescription();
        this.subscriptionResourceId = Long.valueOf(commandDto.getSubscriptionResourceId());
        this.contentIdentifiers = commandDto.getContentIdentifiers().stream()
                .map(i -> new ContentIdentifier(i.getContentIdentifier()))
                .collect(Collectors.toSet());
    }
}
