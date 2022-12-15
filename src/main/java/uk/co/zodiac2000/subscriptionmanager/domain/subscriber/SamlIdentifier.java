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
    public SamlIdentifier(String entityId, String scopedAffiliation) {
        this.entityId = entityId;
        this.scopedAffiliation = scopedAffiliation;
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

    @Override
    public boolean equals(Object other) {
        return other != null
                && this.getClass() == other.getClass()
                && Objects.equals(this.getEntityId(), ((SamlIdentifier) other).getEntityId())
                && Objects.equals(this.getScopedAffiliation(), ((SamlIdentifier) other).getScopedAffiliation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getEntityId(), this.getScopedAffiliation());
    }

    @Override
    public int compareTo(SamlIdentifier other) {
        return Comparator.comparing(SamlIdentifier::getEntityId)
                .thenComparing(SamlIdentifier::getScopedAffiliation)
                .compare(this, other);
    }
}
