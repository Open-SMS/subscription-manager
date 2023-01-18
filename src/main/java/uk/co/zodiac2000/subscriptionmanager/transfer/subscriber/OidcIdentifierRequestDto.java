package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Request DTO representing OIDC claims.
 */
public class OidcIdentifierRequestDto {

    @NotEmpty
    private final String issuer;

    @NotNull
    private final List<OidcIdentifierClaimRequestDto> oidcIdentifierClaims;

    /**
     * @param issuer the iss claim
     * @param oidcIdentifierClaims the claims associated with the issuer
     */
    public OidcIdentifierRequestDto(final String issuer,
            final List<OidcIdentifierClaimRequestDto> oidcIdentifierClaims) {
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
     * @return the claims associated with the issuer
     */
    public List<OidcIdentifierClaimRequestDto> getOidcIdentifierClaims() {
        return this.oidcIdentifierClaims;
    }
}
