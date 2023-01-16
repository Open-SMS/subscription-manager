package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifierResponseDto.
 */
public class OidcIdentifierResponseDtoTest {

    private static final String ISSUER = "https://accounts.google.com";
    private static final List<OidcIdentifierClaimResponseDto> OIDC_CLAIMS = List.of(
            new OidcIdentifierClaimResponseDto("sub", "Zm9vYmFyYmF6Cg=="));

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        OidcIdentifierResponseDto responseDto = new OidcIdentifierResponseDto(ISSUER, OIDC_CLAIMS);

        Assert.assertEquals(responseDto.getIssuer(), ISSUER);
        Assert.assertEquals(responseDto.getOidcIdentifierClaims(), OIDC_CLAIMS);
    }
}
