package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Command DTO representing an OIDC identifier.
 */
public class OidcIdentifierCommandDto {

    @NotEmpty
    private final String issuer;

    @NotNull
    @Valid
    private final Set<OidcIdentifierClaimCommandDto> oidcIdentifierClaims;

    /**
     * @param issuer the iss claim
     * @param oidcIdentifierClaims the the claims associated with this OIDC identifier
     */
    public OidcIdentifierCommandDto(final String issuer,
            final Set<OidcIdentifierClaimCommandDto> oidcIdentifierClaims) {
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
     * @return the the claims associated with this OIDC identifier
     */
    public Set<OidcIdentifierClaimCommandDto> getOidcIdentifierClaims() {
        return this.oidcIdentifierClaims;
    }
}
