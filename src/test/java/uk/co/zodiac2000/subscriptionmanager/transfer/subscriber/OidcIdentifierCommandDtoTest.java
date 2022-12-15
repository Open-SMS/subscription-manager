package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifierCommandDto.
 */
public class OidcIdentifierCommandDtoTest {

    private static final String ISSUER = "https://accounts.google.com";
    private static final String SUBJECT = "3204823904";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        OidcIdentifierCommandDto commandDto = new OidcIdentifierCommandDto(ISSUER, SUBJECT);

        Assert.assertEquals(commandDto.getIssuer(), ISSUER);
        Assert.assertEquals(commandDto.getSubject(), SUBJECT);
    }
}
