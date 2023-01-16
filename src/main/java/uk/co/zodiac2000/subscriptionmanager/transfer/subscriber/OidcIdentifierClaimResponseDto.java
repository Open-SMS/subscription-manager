package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

/**
 * Response DTO representing an OIDC Claim.
 */
public class OidcIdentifierClaimResponseDto {

    private final String name;

    private final String value;

    /**
     * Constructs a new OidcIdentifierClaimResponseDto.
     * @param name the claim name
     * @param value the claim value
     */
    public OidcIdentifierClaimResponseDto(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * @return the claim name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the claim value
     */
    public String getValue() {
        return this.value;
    }
}
