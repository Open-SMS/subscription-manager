package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import javax.validation.constraints.NotEmpty;

/**
 * Request DTO representing a claim value associated with an OIDC claim.
 */
public class OidcIdentifierClaimValueRequestDto {

    @NotEmpty
    private final String claimValue;

    /**
     * Constructs a new OidcIdentifierClaimValueRequestDto.
     * @param claimValue the claim value
     */
    public OidcIdentifierClaimValueRequestDto(final String claimValue) {
        this.claimValue = claimValue;
    }

    /**
     * @return the claim value
     */
    public String getClaimValue() {
        return this.claimValue;
    }
}
