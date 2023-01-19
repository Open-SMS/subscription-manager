package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifierRequestDto.
 */
public class OidcIdentifierRequestDtoTest {

    private static final String ISSUER = "https://accounts.google.com";
    private static final String CLAIM_NAME_SUB = "sub";
    private static final String CLAIM_NAME_GROUPS = "groups";
    private static final List<OidcIdentifierClaimRequestDto> CLAIMS = List.of(
            new OidcIdentifierClaimRequestDto(CLAIM_NAME_SUB, List.of(
                    new OidcIdentifierClaimValueRequestDto("e5389fa9123")
            )),
            new OidcIdentifierClaimRequestDto(CLAIM_NAME_GROUPS, List.of(
                    new OidcIdentifierClaimValueRequestDto("student")
            ))
    );

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        OidcIdentifierRequestDto requestDto = new OidcIdentifierRequestDto(ISSUER, CLAIMS);

        Assert.assertEquals(requestDto.getIssuer(), ISSUER);
        Assert.assertSame(requestDto.getOidcIdentifierClaims(), CLAIMS);
    }

    /**
     * Test getClaimNames.
     */
    @Test
    public void testGetClaimNames() {
        OidcIdentifierRequestDto requestDto = new OidcIdentifierRequestDto(ISSUER, CLAIMS);

        assertThat(requestDto.getClaimNames(), containsInAnyOrder(
                is(CLAIM_NAME_SUB), is(CLAIM_NAME_GROUPS)
        ));
    }
}
