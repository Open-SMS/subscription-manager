package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Request DTO representing a claim associated with an OIDC issuer.
 */
public class OidcIdentifierClaimRequestDto {

    @NotEmpty
    private final String claimName;

    @NotEmpty
    @Valid
    private final List<OidcIdentifierClaimValueRequestDto> claimValues;

    /**
     * Constructs a new OidcIdentifierClaimRequestDto.
     * @param claimName the claim name
     * @param claimValues one or more claim values
     */
    public OidcIdentifierClaimRequestDto(final String claimName,
            final List<OidcIdentifierClaimValueRequestDto> claimValues) {
        this.claimName = claimName;
        this.claimValues = claimValues;
    }

    /**
     * @return the claim name
     */
    public String getClaimName() {
        return this.claimName;
    }

    /**
     * @return one or more claim values
     */
    public List<OidcIdentifierClaimValueRequestDto> getClaimValues() {
        return this.claimValues;
    }

    /**
     * @return one or claims values as a set of strings
     */
    public Set<String> getClaimValuesAsStrings() {
        return this.claimValues.stream().map(v -> v.getClaimValue()).collect(Collectors.toSet());
    }
}
