package uk.co.zodiac2000.subscriptionmanager.domain.subscriber;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifierClaim.
 */
public class OidcIdentifierClaimTest {

    private static final String CLAIM_NAME_ONE = "sub";
    private static final String CLAIM_VALUE_ONE = "Zm9vYmFyYmF6Cg==";
    private static final String CLAIM_NAME_TWO = "groups";
    private static final String CLAIM_VALUE_TWO = "student";

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        OidcIdentifierClaim oidcIdentifierClaim = new OidcIdentifierClaim();

        Assert.assertNotNull(oidcIdentifierClaim);
    }

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        OidcIdentifierClaim oidcIdentifierClaim = new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_ONE);

        Assert.assertEquals(oidcIdentifierClaim.getClaimName(), CLAIM_NAME_ONE);
        Assert.assertEquals(oidcIdentifierClaim.getClaimValue(), CLAIM_VALUE_ONE);
    }

    /**
     * Test constructor when claimName is null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorClaimNameNull() {
        OidcIdentifierClaim oidcIdentifierClaim = new OidcIdentifierClaim(null, CLAIM_VALUE_ONE);
    }

    /**
     * Test constructor when claimValue is null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorClaimValueNull() {
        OidcIdentifierClaim oidcIdentifierClaim = new OidcIdentifierClaim(CLAIM_NAME_ONE, null);
    }

    /**
     * Test equals when argument is null.
     */
    @Test
    public void testEqualsNull() {
        OidcIdentifierClaim oidcIdentifierClaim = new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_ONE);

        Assert.assertFalse(oidcIdentifierClaim.equals(null));
    }

    /**
     * Test equals when argument is a different class.
     */
    @Test
    public void testEqualsDifferent() {
        OidcIdentifierClaim oidcIdentifierClaim = new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_ONE);

        Assert.assertFalse(oidcIdentifierClaim.equals("foo"));
    }

    /**
     * Test equals when the claimName fields are not equal.
     */
    @Test
    public void testEqualsDifferentClaimName() {
        OidcIdentifierClaim oidcIdentifierClaimOne = new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_ONE);
        OidcIdentifierClaim oidcIdentifierClaimTwo = new OidcIdentifierClaim(CLAIM_NAME_TWO, CLAIM_VALUE_ONE);

        Assert.assertFalse(oidcIdentifierClaimOne.equals(oidcIdentifierClaimTwo));
    }

    /**
     * Test equals when the claimName fields are equal but the claimValue fields are not equal.
     */
    @Test
    public void testEqualsDifferentClaimValue() {
        OidcIdentifierClaim oidcIdentifierClaimOne = new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_ONE);
        OidcIdentifierClaim oidcIdentifierClaimTwo = new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_TWO);

        Assert.assertFalse(oidcIdentifierClaimOne.equals(oidcIdentifierClaimTwo));
    }

    /**
     * Test equals when the objects are equal.
     */
    @Test
    public void testEquals() {
        OidcIdentifierClaim oidcIdentifierClaimOne = new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_ONE);
        OidcIdentifierClaim oidcIdentifierClaimTwo = new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_ONE);

        Assert.assertTrue(oidcIdentifierClaimOne.equals(oidcIdentifierClaimTwo));
    }

    /**
     * Test hashcode.
     */
    @Test
    public void testHashCode() {
        OidcIdentifierClaim oidcIdentifierClaimOne = new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_ONE);
        OidcIdentifierClaim oidcIdentifierClaimTwo = new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_ONE);

        Assert.assertEquals(oidcIdentifierClaimOne.hashCode(), oidcIdentifierClaimTwo.hashCode());
    }

    /**
     * Test compareTo when the objects  are equal.
     */
    @Test
    public void testCompareToEqual() {
        OidcIdentifierClaim oidcIdentifierClaimOne = new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_ONE);
        OidcIdentifierClaim oidcIdentifierClaimTwo = new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_ONE);

        Assert.assertEquals(oidcIdentifierClaimOne.compareTo(oidcIdentifierClaimTwo), 0);
    }

    /**
     * Test compareTo when the claimName fields are not equal.
     */
    @Test
    public void testCompareToClaimNameNotEqual() {
        OidcIdentifierClaim oidcIdentifierClaimOne = new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_ONE);
        OidcIdentifierClaim oidcIdentifierClaimTwo = new OidcIdentifierClaim(CLAIM_NAME_TWO, CLAIM_VALUE_ONE);

        Assert.assertTrue(oidcIdentifierClaimOne.compareTo(oidcIdentifierClaimTwo) > 0);
    }

    /**
     * Test compareTo when the claimName fields are not equal.
     */
    @Test
    public void testCompareToClaimValueNotEqual() {
        OidcIdentifierClaim oidcIdentifierClaimOne = new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_ONE);
        OidcIdentifierClaim oidcIdentifierClaimTwo = new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_TWO);

        Assert.assertTrue(oidcIdentifierClaimOne.compareTo(oidcIdentifierClaimTwo) < 0);
    }

}
