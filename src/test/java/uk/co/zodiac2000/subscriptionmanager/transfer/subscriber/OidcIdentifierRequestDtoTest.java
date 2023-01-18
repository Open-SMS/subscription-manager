package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifierRequestDto.
 */
public class OidcIdentifierRequestDtoTest {

    private static final String ISSUER = "https://accounts.google.com";
    private static final List<OidcIdentifierClaimRequestDto> CLAIMS = List.of();

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        OidcIdentifierRequestDto requestDto = new OidcIdentifierRequestDto(ISSUER, CLAIMS);

        Assert.assertEquals(requestDto.getIssuer(), ISSUER);
        Assert.assertSame(requestDto.getOidcIdentifierClaims(), CLAIMS);
    }
}
