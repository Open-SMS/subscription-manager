package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import javax.validation.constraints.NotEmpty;

/**
 * Response DTO representing an OIDC Claim.
 */
public class OidcIdentifierClaimCommandDto {

    @NotEmpty
    private final String name;

    @NotEmpty
    private final String value;

    /**
     * Constructs a new OidcIdentifierClaimResponseDto.
     * @param name the claim name
     * @param value the claim value
     */
    public OidcIdentifierClaimCommandDto(String name, String value) {
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
