package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifierClaimResponseDto.
 */
public class OidcIdentifierClaimResponseDtoTest {

    private static final String CLAIM_NAME = "sub";
    private static final String CLAIM_VALUE = "Zm9vYmFyYmF6Cg==";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        OidcIdentifierClaimResponseDto dto = new OidcIdentifierClaimResponseDto(CLAIM_NAME, CLAIM_VALUE);

        Assert.assertEquals(dto.getClaimName(), CLAIM_NAME);
        Assert.assertEquals(dto.getClaimValue(), CLAIM_VALUE);
    }
}
