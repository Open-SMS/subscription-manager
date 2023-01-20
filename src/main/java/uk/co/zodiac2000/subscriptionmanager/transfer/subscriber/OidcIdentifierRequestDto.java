package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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

    /**
     * This method may not be required.
     * @return a set of all the claim names used for claims in this request
     */
    public Set<String> getClaimNames() {
        return this.oidcIdentifierClaims.stream()
                .map(OidcIdentifierClaimRequestDto::getClaimName)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a new OidcIdentifierRequestDto containing only claims with claim names that occur in the
     * {@code requiredClaimNames} argument.
     * @param requiredClaimNames a set of claim names indicating the required claims
     * @return a new OidcIdentifierRequestDto object
     */
    public OidcIdentifierRequestDto createFilteredRequest(Set<String> requiredClaimNames) {
        List<OidcIdentifierClaimRequestDto> filteredClaims = this.oidcIdentifierClaims.stream()
                .filter(c -> requiredClaimNames.contains(c.getClaimName()))
                .map(c -> new OidcIdentifierClaimRequestDto(c.getClaimName(), c.getClaimValues()))
                .collect(Collectors.toList());
        return new OidcIdentifierRequestDto(this.getIssuer(), filteredClaims);
    }
}
