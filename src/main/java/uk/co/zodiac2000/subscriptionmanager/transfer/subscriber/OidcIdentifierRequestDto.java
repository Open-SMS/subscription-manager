package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;

/**
 * Request DTO representing OIDC claims.
 */
public class OidcIdentifierRequestDto {

    @NotEmpty
    private final String issuer;

    @NotEmpty
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

    /**
     * Returns a new OidcIdentifierRequestDto containing only claims with claim names that occur in the
     * {@code requiredClaimNames} argument.
     * @param requiredClaimNames a set of claim names indicating the required claims
     * @return a new OidcIdentifierRequestDto object
     */
    public OidcIdentifierRequestDto createFilteredRequest(final Set<String> requiredClaimNames) {
        List<OidcIdentifierClaimRequestDto> filteredClaims = this.oidcIdentifierClaims.stream()
                .filter(c -> requiredClaimNames.contains(c.getClaimName()))
                .map(c -> new OidcIdentifierClaimRequestDto(c.getClaimName(), c.getClaimValues()))
                .collect(Collectors.toList());
        return new OidcIdentifierRequestDto(this.getIssuer(), filteredClaims);
    }


    /**
     * Returns true if the {@code requiredClaimName} argument matches claimName, and claimValues contains a value
     * matching the {@code requiredClaimValue} argument.
     * @param requiredClaimName the required claim name
     * @param requiredClaimValue the required claim value
     * @return true if the required claim was matched
     */
    public boolean matchesClaims(final String requiredClaimName, final String requiredClaimValue) {
        return this.oidcIdentifierClaims.stream()
                .filter(c -> c.getClaimName().equals(requiredClaimName)
                        && c.getClaimValuesAsStrings().contains(requiredClaimValue)
                )
                .findFirst().isPresent();
    }
}
