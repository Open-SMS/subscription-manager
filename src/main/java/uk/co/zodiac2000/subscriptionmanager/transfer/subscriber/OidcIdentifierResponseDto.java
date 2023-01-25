package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;

/**
 * Response DTO representing an OIDC identifier.
 */
public class OidcIdentifierResponseDto {

    private final String issuer;

    private final List<OidcIdentifierClaimResponseDto> oidcIdentifierClaims;

    /**
     * Constructs a new OidcIdentifierResponseDto.
     * @param issuer the iss claim
     * @param oidcIdentifierClaims a list of OIDC claims
     */
    public OidcIdentifierResponseDto(final String issuer,
            final List<OidcIdentifierClaimResponseDto> oidcIdentifierClaims) {
        this.issuer = issuer;
        this.oidcIdentifierClaims = oidcIdentifierClaims;
    }

    /**
     * @return the iss claim
     */
    public String getIssuer() {
        return this.issuer;
    }

    /**
     * @return the OIDC claims
     */
    public List<OidcIdentifierClaimResponseDto> getOidcIdentifierClaims() {
        return this.oidcIdentifierClaims;
    }
}
