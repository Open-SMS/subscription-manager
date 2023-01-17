package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import javax.validation.constraints.NotEmpty;

/**
 * Response DTO representing an OIDC Claim.
 */
public class OidcIdentifierClaimCommandDto {

    @NotEmpty
    private final String claimName;

    @NotEmpty
    private final String claimValue;

    /**
     * Constructs a new OidcIdentifierClaimResponseDto.
     * @param name the claim name
     * @param value the claim value
     */
    public OidcIdentifierClaimCommandDto(String name, String value) {
        this.claimName = name;
        this.claimValue = value;
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
