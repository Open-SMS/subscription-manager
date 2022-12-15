package uk.co.zodiac2000.subscriptionmanager.domain.subscriber;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

/**
 * Value object representing an OpenID Connect sub claim and the issuer. This will initially be how OIDC users are identified
 * because this claim will uniquely identify a user. In future more claims may be supported.
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
    public OidcIdentifier(String issuer, String subject) {
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

    @Override
    public boolean equals(Object other) {
        return other != null
                && this.getClass() == other.getClass()
                && Objects.equals(this.getIssuer(), ((OidcIdentifier) other).getIssuer())
                && Objects.equals(this.getSubject(), ((OidcIdentifier) other).getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getIssuer(), this.getSubject());
    }

    @Override
    public int compareTo(OidcIdentifier other) {
        return Comparator.comparing(OidcIdentifier::getIssuer)
                .thenComparing(OidcIdentifier::getSubject)
                .compare(this, other);
    }
}
