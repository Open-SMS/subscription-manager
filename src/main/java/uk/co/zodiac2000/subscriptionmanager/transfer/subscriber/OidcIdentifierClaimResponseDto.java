package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

/**
 * Response DTO representing an OIDC Claim.
 */
public class OidcIdentifierClaimResponseDto {

    private final String claimName;

    private final String claimValue;

    /**
     * Constructs a new OidcIdentifierClaimResponseDto.
     * @param claimName the claim name
     * @param claimValue the claim value
     */
    public OidcIdentifierClaimResponseDto(String claimName, String claimValue) {
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
