package uk.co.zodiac2000.subscriptionmanager.domain.subscriber;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierRequestDto;
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
    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OidcIdentifier> oidcIdentifiers = new HashSet<>();

    /**
     * Zero-args constructor for JPA.
     */
    public Subscriber() { }

    /**
     * Constructs a new Subscriber using the supplied arguments.
     * @param subscriberName the subscriber's name
     */
    public Subscriber(final String subscriberName) {
        this.subscriberName = subscriberName;
    }

    /**
     * Allows the subscriber name to be changed.
     * @param subscriberNameCommandDto the subscriber's name
     */
    public void setSubscriberName(final SubscriberNameCommandDto subscriberNameCommandDto) {
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
    public void setSamlIdentifiers(final Set<SamlIdentifierCommandDto> samlIdentifiers) {
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
     * @param oidcIdentifiers the OIDC identifiers
     */
    public void setOidcIdentifiers(final Set<OidcIdentifierCommandDto> oidcIdentifiers) {
        this.oidcIdentifiers.stream().forEach(i -> i.removeSubscriber());
        this.oidcIdentifiers.clear();
        this.oidcIdentifiers.addAll(
                oidcIdentifiers.stream()
                .map(i -> new OidcIdentifier(i.getIssuer(),
                i.getOidcIdentifierClaims().stream()
                        .map(c -> new OidcIdentifierClaim(c.getClaimName(), c.getClaimValue()))
                        .collect(Collectors.toSet()),
                this))
                .collect(Collectors.toSet())
        );
    }

    /**
     * @return the OIDC identifiers associated with this subscriber
     */
    public Set<OidcIdentifier> getOidcIdentifiers() {
        return Set.copyOf(this.oidcIdentifiers);
    }

    /**
     * Returns true if the claims in the {@code oidcIdentifierRequest} parameter can completely match the issuer and
     * claims associated with an OIDC identifier which is associated with this subscriber.
     * @param oidcIdentifierRequest request DTO the contains the issuer and claims
     * @return true if claims requirements are met
     */
    public boolean claimsSatisfyRequirements(final OidcIdentifierRequestDto oidcIdentifierRequest) {
        return this.oidcIdentifiers.stream()
                .filter(i -> i.claimsSatisfyRequirements(oidcIdentifierRequest))
                .findFirst().isPresent();
    }
}
