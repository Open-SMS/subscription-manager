package uk.co.zodiac2000.subscriptionmanager.domain.subscriber;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierRequestDto;

/**
 * Value object representing an OpenID Connect issuer and a set of claims. The authorization request must satisfy
 * all claims for the specified issuer for the associated subscriber to be identified. Satisfaction of the claims
 * requires all OIDC identifier claims associated with this object to be present in the authorization request.
 */
@Entity
public class OidcIdentifier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "oidc_identifier_id_gen", sequenceName = "oidc_identifier_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "oidc_identifier_id_gen")
    private Long id;

    @NotEmpty
    private String issuer;

    @Valid
    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "oidc_identifier_claim")
    private Set<OidcIdentifierClaim> oidcIdentifierClaims;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;

    /**
     * Zero-args constructor for JPA.
     */
    public OidcIdentifier() { }

    /**
     * Constructs a new OidcIdentifier with the supplied arguments. The {@code oidcIdentifierClaims}
     * argument is required to contain at least one member.
     * @param issuer the iss claim
     * @param oidcIdentifierClaims the set of OIDC claims associated with this OIDC identifier
     * @param subscriber the subscriber that this OidcIdentifier is associated with
     * @throws NullPointerException if any argument is null
     * @throws IllegalArgumentException if the oidcIdentifierClaims set does not contain at least one member
     */
    public OidcIdentifier(final String issuer, final Set<OidcIdentifierClaim> oidcIdentifierClaims,
            final Subscriber subscriber) {
        this.issuer = Objects.requireNonNull(issuer);
        this.oidcIdentifierClaims = Objects.requireNonNull(oidcIdentifierClaims);
        this.subscriber = Objects.requireNonNull(subscriber);
        if (this.oidcIdentifierClaims.isEmpty()) {
            throw new IllegalArgumentException("The oidcIdentifierClaims argument must contain at least one member");
        }
    }

    /**
     * @return the iss claim
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * @return the set of OIDC claims associated with this OIDC identifier
     */
    public Set<OidcIdentifierClaim> getOidcIdentifierClaims() {
        return Set.copyOf(this.oidcIdentifierClaims);
    }

    /**
     * Removes the reference to the subscriber owning this OIDC identifier.
     */
    public void removeSubscriber() {
        this.subscriber = null;
    }

    /**
     * Indicates whether {@code other} is equal to this object. Non-null objects of the same class and with
     * equal {@code issuer} and {@code subject} fields are considered equal.
     * @param other the reference object with which to compare
     * @return true if this object is equal to the {@code other} argument; false otherwise.
     */
    @Override
    public boolean equals(final Object other) {
        return other != null
                && this.getClass() == other.getClass()
                && Objects.equals(this.getIssuer(), ((OidcIdentifier) other).getIssuer())
                && Objects.equals(this.getOidcIdentifierClaims(), ((OidcIdentifier) other).getOidcIdentifierClaims());
    }

    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getIssuer(), this.getOidcIdentifierClaims());
    }

    /**
     * Returns true if the issuer and claims represented by {@code oidcIdentifierRequest} match the issuer and claims
     * represented by this OidcIdentifier.
     * @param oidcIdentifierRequest a request containing claims submitted by the client
     * @return true if  the request meets the requirements of this OidcIdentifier
     */
    public boolean claimsSatisfyRequirements(final OidcIdentifierRequestDto oidcIdentifierRequest) {
        if (this.issuer.equals(oidcIdentifierRequest.getIssuer())) {
            long claimsMatched = this.oidcIdentifierClaims.stream()
                    .filter(c -> oidcIdentifierRequest.matchesClaims(c.getClaimName(), c.getClaimValue()))
                    .count();
            return claimsMatched == this.oidcIdentifierClaims.size();
        } else {
            return false;
        }
    }
}
