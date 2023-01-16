package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifierClaimCommandDto.
 */
public class OidcIdentifierClaimCommandDtoTest {

    private static final String CLAIM_NAME = "sub";
    private static final String CLAIM_VALUE = "Zm9vYmFyYmF6Cg==";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        OidcIdentifierClaimCommandDto dto = new OidcIdentifierClaimCommandDto(CLAIM_NAME, CLAIM_VALUE);

        Assert.assertEquals(dto.getName(), CLAIM_NAME);
        Assert.assertEquals(dto.getValue(), CLAIM_VALUE);
    }
}
