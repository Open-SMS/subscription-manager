package uk.co.zodiac2000.subscriptionmanager.domain.subscriber;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

/**
 * Value object representing an OpenID Connect sub claim and the issuer. This will initially be how OIDC users are
 * identified because this claim will uniquely identify a user. In future more claims may be supported.
 * See https://openid.net/specs/openid-connect-core-1_0.html#ClaimStability
 */
@Embeddable
public class OidcIdentifier implements Serializable, Comparable<OidcIdentifier> {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String issuer;

    @NotEmpty
    private String subject;

    /**
     * Zero-args constructor for JPA.
     */
    public OidcIdentifier() { }

    /**
     * @param issuer the iss claim
     * @param subject the sub claim
     */
    public OidcIdentifier(final String issuer, final String subject) {
        this.issuer = Objects.requireNonNull(issuer);
        this.subject = Objects.requireNonNull(subject);
    }

    /**
     * @return the iss claim
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * @return the sub claim
     */
    public String getSubject() {
        return subject;
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
                && Objects.equals(this.getSubject(), ((OidcIdentifier) other).getSubject());
    }

    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getIssuer(), this.getSubject());
    }

    /**
     * Compares this object with the specified object for order. Returns a negative integer, zero, or a positive
     * integer as this object is less than, equal to, or greater than the specified object. Comparison is based
     * on comparison of the {@code issuer} and then {@subject} fields.
     * @param other the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
     * the specified object.
     */
    @Override
    public int compareTo(final OidcIdentifier other) {
        return Comparator.comparing(OidcIdentifier::getIssuer)
                .thenComparing(OidcIdentifier::getSubject)
                .compare(this, other);
    }
}
