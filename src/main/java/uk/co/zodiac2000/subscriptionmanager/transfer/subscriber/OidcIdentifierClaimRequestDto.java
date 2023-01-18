package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import javax.validation.constraints.NotEmpty;

/**
 * Request DTO representing a claim associated with an OIDC issuer.
 */
public class OidcIdentifierClaimRequestDto {

    @NotEmpty
    private final String claimName;

    @NotEmpty
    private final String claimValue;

    /**
     * Constructs a new OidcIdentifierClaimRequestDto.
     * @param claimName the claim name
     * @param claimValue the claim value
     */
    public OidcIdentifierClaimRequestDto(final String claimName, final String claimValue) {
        this.claimName = claimName;
        this.claimValue = claimValue;
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
}
