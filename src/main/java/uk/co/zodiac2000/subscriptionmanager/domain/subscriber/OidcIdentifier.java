package uk.co.zodiac2000.subscriptionmanager.domain.subscriber;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

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
    @ElementCollection
    @CollectionTable(name = "oidc_identifier_claim")
    private Set<OidcIdentifierClaim> oidcIdentifierClaims;

    /**
     * Zero-args constructor for JPA.
     */
    public OidcIdentifier() { }

    /**
     * @param issuer the iss claim
     * @param oidcIdentifierClaims the set of OIDC claims associated with this OIDC identifier
     */
    public OidcIdentifier(final String issuer, final Set<OidcIdentifierClaim> oidcIdentifierClaims) {
        this.issuer = Objects.requireNonNull(issuer);
        this.oidcIdentifierClaims = Objects.requireNonNull(oidcIdentifierClaims);
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
}
