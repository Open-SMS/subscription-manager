package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifierCommandDto.
 */
public class OidcIdentifierCommandDtoTest {

    private static final String CLAIM_NAME_SUB = "sub";
    private static final String CLAIM_NAME_GROUPS = "groups";
    private static final String ISSUER = "https://accounts.google.com";
    private static final List<OidcIdentifierClaimCommandDto> CLAIMS = List.of(
            new OidcIdentifierClaimCommandDto(CLAIM_NAME_SUB, "897348"),
            new OidcIdentifierClaimCommandDto(CLAIM_NAME_GROUPS, "member")
    );

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        OidcIdentifierCommandDto commandDto = new OidcIdentifierCommandDto(ISSUER, CLAIMS);

        Assert.assertEquals(commandDto.getIssuer(), ISSUER);
        Assert.assertEquals(commandDto.getOidcIdentifierClaims(), CLAIMS);
    }


    /**
     * Test getClaimNames.
     */
    @Test
    public void testGetClaimNames() {
        OidcIdentifierCommandDto commandDto = new OidcIdentifierCommandDto(ISSUER, CLAIMS);

        assertThat(commandDto.getClaimNames(), containsInAnyOrder(
                is(CLAIM_NAME_SUB), is(CLAIM_NAME_GROUPS)
        ));
    }
}
