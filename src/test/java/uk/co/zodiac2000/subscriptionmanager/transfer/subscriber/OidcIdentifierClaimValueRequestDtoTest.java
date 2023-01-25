package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifierClaimValueRequestDto.
 */
public class OidcIdentifierClaimValueRequestDtoTest {

    private static final String CLAIM_VALUE = "Zm9vYmFyYmF6Cg==";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        OidcIdentifierClaimValueRequestDto dto = new OidcIdentifierClaimValueRequestDto(CLAIM_VALUE);

        Assert.assertEquals(dto.getClaimValue(), CLAIM_VALUE);
    }
}
