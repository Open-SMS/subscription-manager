package uk.co.zodiac2000.subscriptionmanager.domain.subscriber;

import java.io.Serializable;
import java.util.HashSet;
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
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SamlIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SubscriberNameCommandDto;

/**
 * Represents a subscriber with an identity within the subscription management system.
 */
@Entity
public class Subscriber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "subscriber_id_gen", sequenceName = "subscriber_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "subscriber_id_gen")
    private Long id;

    @NotEmpty
    private String subscriberName;

    @Valid
    @ElementCollection
    @CollectionTable(name = "saml_identifier")
    private Set<SamlIdentifier> samlIdentifiers = new HashSet<>();

    @Valid
    @ElementCollection
    @CollectionTable(name = "oidc_identifier")
    private Set<OidcIdentifier> oidcIdentifiers = new HashSet<>();

    /**
     * Zero-args constructor for JPA.
     */
    public Subscriber() { }

    /**
     * Constructs a new Subscriber using the supplied arguments.
     * @param subscriberName the subscriber's name
     */
    public Subscriber(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    /**
     * Allows the subscriber name to be changed.
     * @param subscriberNameCommandDto the subscriber's name
     */
    public void setSubscriberName(SubscriberNameCommandDto subscriberNameCommandDto) {
        this.subscriberName = subscriberNameCommandDto.getSubscriberName();
    }

    /**
     * @return the subscriber identifier
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @return the subscriber's name
     */
    public String getSubscriberName() {
        return this.subscriberName;
    }

    /**
     * Sets the SAML identifiers associated with this subscriber.
     * @param samlIdentifiers the SAML identifiers
     */
    public void setSamlIdentifiers(Set<SamlIdentifierCommandDto> samlIdentifiers) {
        this.samlIdentifiers = samlIdentifiers.stream()
                .map(i -> new SamlIdentifier(i.getEntityId(), i.getScopedAffiliation()))
                .collect(Collectors.toSet());
    }

    /**
     * @return the SAML identifiers associated with this subscriber
     */
    public Set<SamlIdentifier> getSamlIdentifiers() {
        return Set.copyOf(this.samlIdentifiers);
    }

    /**
     * Sets the OIDC identifiers associated with this subscriber.
     * @param samlIdentifiers the SAML identifiers
     */
    public void setOidcIdentifiers(Set<OidcIdentifierCommandDto> samlIdentifiers) {
        this.oidcIdentifiers = samlIdentifiers.stream()
                .map(i -> new OidcIdentifier(i.getIssuer(), i.getSubject()))
                .collect(Collectors.toSet());
    }

    /**
     * @return the SAML identifiers associated with this subscriber
     */
    public Set<OidcIdentifier> getOidcIdentifiers() {
        return Set.copyOf(this.oidcIdentifiers);
    }
}
