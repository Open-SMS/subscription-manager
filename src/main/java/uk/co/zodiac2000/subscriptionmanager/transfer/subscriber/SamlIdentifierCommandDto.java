package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import javax.validation.constraints.NotEmpty;

/**
 * Command DTO representing a SAML Identifier.
 */
public class SamlIdentifierCommandDto {

    @NotEmpty
    private final String entityId;

    @NotEmpty
    private final String scopedAffiliation;

    /**
     * @param entityId the issuer's entityId
     * @param scopedAffiliation the value of the eduPersonScopedAffiliation attribute released by the issuer
     */
    public SamlIdentifierCommandDto(String entityId, String scopedAffiliation) {
        this.entityId = entityId;
        this.scopedAffiliation = scopedAffiliation;
    }

    /**
     * @return the issuer's entityId
     */
    public String getEntityId() {
        return this.entityId;
    }

    /**
     * @return the value of the eduPersonScopedAffiliation attribute released by the issuer
     */
    public String getScopedAffiliation() {
        return this.scopedAffiliation;
    }
}
