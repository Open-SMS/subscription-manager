package uk.co.zodiac2000.subscriptionmanager.domain.subscriber;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

/**
 * Value object representing a SAML authentication identifier. Initially this will be composed of the entityID of the
 * issuing IdP and the eduPersonScopedAffiliation Shibboleth attribute. This is expected to change as this feature
 * develops.
 */
@Embeddable
public class SamlIdentifier implements Serializable, Comparable<SamlIdentifier> {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String entityId;

    @NotEmpty
    private String scopedAffiliation;

    /**
     * Zero-args constructor for JPA.
     */
    public SamlIdentifier() { }

    /**
     * @param entityId the issuer's entityId
     * @param scopedAffiliation the value of the eduPersonScopedAffiliation attribute released by the issuer
     */
    public SamlIdentifier(final String entityId, final String scopedAffiliation) {
        this.entityId = Objects.requireNonNull(entityId);
        this.scopedAffiliation = Objects.requireNonNull(scopedAffiliation);
    }

    /**
     * @return the issuer's entityId
     */
    public String getEntityId() {
        return entityId;
    }

    /**
     * @return the value of the eduPersonScopedAffiliation attribute released by the issuer
     */
    public String getScopedAffiliation() {
        return scopedAffiliation;
    }

    /**
     * Indicates whether {@code other} is equal to this object. Non-null objects of the same class and with
     * equal {@code entityId} and {@code scopedAffiliation} fields are considered equal.
     * @param other the reference object with which to compare
     * @return true if this object is equal to the {@code other} argument; false otherwise.
     */
    @Override
    public boolean equals(final Object other) {
        return other != null
                && this.getClass() == other.getClass()
                && Objects.equals(this.getEntityId(), ((SamlIdentifier) other).getEntityId())
                && Objects.equals(this.getScopedAffiliation(), ((SamlIdentifier) other).getScopedAffiliation());
    }

    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getEntityId(), this.getScopedAffiliation());
    }

    /**
     * Compares this object with the specified object for order. Returns a negative integer, zero, or a positive
     * integer as this object is less than, equal to, or greater than the specified object. Comparison is based
     * on comparison of the {@code entityId} and then {@code scopedAffiliation} fields.
     * @param other the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
     * the specified object.
     */
    @Override
    public int compareTo(final SamlIdentifier other) {
        return Comparator.comparing(SamlIdentifier::getEntityId)
                .thenComparing(SamlIdentifier::getScopedAffiliation)
                .compare(this, other);
    }
}
