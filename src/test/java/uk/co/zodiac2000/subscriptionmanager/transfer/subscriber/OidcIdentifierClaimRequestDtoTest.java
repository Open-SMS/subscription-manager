package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifierClaimRequestDto.
 */
public class OidcIdentifierClaimRequestDtoTest {

    private static final String CLAIM_NAME = "sub";
    private static final String CLAIM_VALUE = "2834912458";
    private static final List<OidcIdentifierClaimValueRequestDto> CLAIM_VALUES = List.of(
            new OidcIdentifierClaimValueRequestDto(CLAIM_VALUE)
    );

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        OidcIdentifierClaimRequestDto dto = new OidcIdentifierClaimRequestDto(CLAIM_NAME, CLAIM_VALUES);

        Assert.assertEquals(dto.getClaimName(), CLAIM_NAME);
        Assert.assertEquals(dto.getClaimValues(), CLAIM_VALUES);
        assertThat(dto.getClaimValuesAsStrings(), contains(is(CLAIM_VALUE)));
    }
}
