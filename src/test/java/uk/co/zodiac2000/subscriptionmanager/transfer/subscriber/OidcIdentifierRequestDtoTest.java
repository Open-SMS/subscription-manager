package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifierRequestDto.
 */
public class OidcIdentifierRequestDtoTest {

    private static final String ISSUER = "https://accounts.google.com";
    private static final String SUBJECT = "3204823904";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        OidcIdentifierRequestDto requestDto = new OidcIdentifierRequestDto(ISSUER, SUBJECT);

        Assert.assertEquals(requestDto.getIssuer(), ISSUER);
        Assert.assertEquals(requestDto.getSubject(), SUBJECT);
    }
}
