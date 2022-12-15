package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifierResponseDto.
 */
public class OidcIdentifierResponseDtoTest {

    private static final String ISSUER = "https://accounts.google.com";
    private static final String SUBJECT = "3204823904";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        OidcIdentifierResponseDto responseDto = new OidcIdentifierResponseDto(ISSUER, SUBJECT);

        Assert.assertEquals(responseDto.getIssuer(), ISSUER);
        Assert.assertEquals(responseDto.getSubject(), SUBJECT);
    }
}
