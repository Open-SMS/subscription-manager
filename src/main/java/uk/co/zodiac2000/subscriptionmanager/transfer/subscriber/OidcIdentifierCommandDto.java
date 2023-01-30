package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Command DTO representing an OIDC identifier.
 */
public class OidcIdentifierCommandDto {

    @NotEmpty
    private final String issuer;

    @NotEmpty
    @Valid
    private final List<OidcIdentifierClaimCommandDto> oidcIdentifierClaims;

    /**
     * @param issuer the iss claim
     * @param oidcIdentifierClaims the the claims associated with this OIDC identifier
     */
    public OidcIdentifierCommandDto(final String issuer,
            final List<OidcIdentifierClaimCommandDto> oidcIdentifierClaims) {
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
    public List<OidcIdentifierClaimCommandDto> getOidcIdentifierClaims() {
        return this.oidcIdentifierClaims;
    }

    /**
     * Returns a set of the claim names used in the associated {@code OidcIdentifierClaimCommandDto} objects.
     * @return a set of claim names
     */
    public Set<String> getClaimNames() {
        return this.oidcIdentifierClaims.stream()
                .map(c -> c.getClaimName())
                .collect(Collectors.toSet());
    }
}
