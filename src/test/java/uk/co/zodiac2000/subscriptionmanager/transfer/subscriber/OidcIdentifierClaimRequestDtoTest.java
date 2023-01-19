package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifierClaimRequestDto.
 */
public class OidcIdentifierClaimRequestDtoTest {

    private static final String CLAIM_NAME = "sub";
    private static final List<OidcIdentifierClaimValueRequestDto> CLAIM_VALUES = List.of();

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        OidcIdentifierClaimRequestDto dto = new OidcIdentifierClaimRequestDto(CLAIM_NAME, CLAIM_VALUES);

        Assert.assertEquals(dto.getClaimName(), CLAIM_NAME);
        Assert.assertEquals(dto.getClaimValues(), CLAIM_VALUES);
    }
}
