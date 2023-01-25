package uk.co.zodiac2000.subscriptionmanager.domain.oidcclaims;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for ClaimName.
 */
public class ClaimNameTest {

    private static final String CLAIM_NAME = "derivedEduPersonScope";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        ClaimName claimName = new ClaimName(CLAIM_NAME);

        Assert.assertNull(claimName.getId());
        Assert.assertEquals(claimName.getClaimName(), CLAIM_NAME);
    }

    /**
     * Test zero-args constructor.
     */
    @Test
    public void testZeroArgsConstructor() {
        ClaimName claimName = new ClaimName();

        Assert.assertNotNull(claimName);
    }
}
