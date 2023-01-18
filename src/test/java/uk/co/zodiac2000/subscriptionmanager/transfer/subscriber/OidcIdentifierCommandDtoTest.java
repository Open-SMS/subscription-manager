package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifierCommandDto.
 */
public class OidcIdentifierCommandDtoTest {

    private static final String ISSUER = "https://accounts.google.com";
    private static final List<OidcIdentifierClaimCommandDto> CLAIMS = List.of();

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        OidcIdentifierCommandDto commandDto = new OidcIdentifierCommandDto(ISSUER, CLAIMS);

        Assert.assertEquals(commandDto.getIssuer(), ISSUER);
        Assert.assertEquals(commandDto.getOidcIdentifierClaims(), CLAIMS);
    }
}
