package uk.co.zodiac2000.subscriptionmanager.domain.subscriber;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Value object representing an OIDC Claim.
 */
@Embeddable
public class OidcIdentifierClaim implements Serializable, Comparable<OidcIdentifierClaim> {

    private static final long serialVersionUID = 1L;

    private static final int MAX_FIELD_LENGTH = 1000;

    @NotEmpty
    @Size(max = MAX_FIELD_LENGTH)
    private String claimName;

    @NotEmpty
    @Size(max = MAX_FIELD_LENGTH)
    private String claimValue;

    /**
     * Zero-args constructor for JPA.
     */
    public OidcIdentifierClaim() { }

    /**
     * Constructs a new OidcIdentifierClaim with the supplied arguments.
     * @param claimName the claim name
     * @param claimValue the claim value
     */
    public OidcIdentifierClaim(final String claimName, final String claimValue) {
        this.claimName = Objects.requireNonNull(claimName);
        this.claimValue = Objects.requireNonNull(claimValue);
    }

    /**
     * @return the claim name
     */
    public String getClaimName() {
        return this.claimName;
    }

    /**
     * @return the claim value
     */
    public String getClaimValue() {
        return this.claimValue;
    }

    /**
     * Indicates whether {@code other} is equal to this object. Non-null objects of the same class and with
     * equal {@code claimName} and {@code claimValue} fields are considered equal.
     * @param other the reference object with which to compare
     * @return true if this object is equal to the {@code other} argument; false otherwise.
     */
    @Override
    public boolean equals(final Object other) {
        return other != null
                && this.getClass() == other.getClass()
                && Objects.equals(this.getClaimName(), ((OidcIdentifierClaim) other).getClaimName())
                && Objects.equals(this.getClaimValue(), ((OidcIdentifierClaim) other).getClaimValue());
    }

    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getClaimName(), this.getClaimValue());
    }

    /**
     * Compares this object with the specified object for order. Returns a negative integer, zero, or a positive
     * integer as this object is less than, equal to, or greater than the specified object. Comparison is based
     * on comparison of the {@code claimName} and then {@code claimValue} fields.
     * @param other the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
     * the specified object.
     */
    @Override
    public int compareTo(final OidcIdentifierClaim other) {
        return Comparator.comparing(OidcIdentifierClaim::getClaimName)
                .thenComparing(OidcIdentifierClaim::getClaimValue)
                .compare(this, other);
    }
}
