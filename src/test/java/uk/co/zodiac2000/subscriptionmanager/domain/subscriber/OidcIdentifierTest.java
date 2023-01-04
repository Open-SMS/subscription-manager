package uk.co.zodiac2000.subscriptionmanager.domain.subscriber;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifier.
 */
public class OidcIdentifierTest {

    private static final String ISSUER_ONE = "https://accounts.google.com";
    private static final String ISSUER_TWO = "https://www.facebook.com";
    private static final String SUBJECT_ONE = "3204823904";
    private static final String SUBJECT_TWO = "d2liYmxlCg==";

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        OidcIdentifier identifier = new OidcIdentifier();

        Assert.assertNotNull(identifier);
    }

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        OidcIdentifier identifier = new OidcIdentifier(ISSUER_ONE, SUBJECT_ONE);

        Assert.assertEquals(identifier.getIssuer(), ISSUER_ONE);
        Assert.assertEquals(identifier.getSubject(),  SUBJECT_ONE);
    }

    /**
     * Test constructor when issuer is null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorIssuerNull() {
        OidcIdentifier identifier = new OidcIdentifier(null, SUBJECT_ONE);
    }

    /**
     * Test constructor when subject is null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorSubjectNull() {
        OidcIdentifier identifier = new OidcIdentifier(ISSUER_ONE, null);
    }

    /**
     * Test equals when argument is null.
     */
    @Test
    public void testEqualsNull() {
        OidcIdentifier identifier = new OidcIdentifier(ISSUER_ONE, SUBJECT_ONE);

        Assert.assertFalse(identifier.equals(null));
    }

    /**
     * Test equals when argument is a different class.
     */
    @Test
    public void testEqualsDifferent() {
        OidcIdentifier identifier = new OidcIdentifier(ISSUER_ONE, SUBJECT_ONE);

        Assert.assertFalse(identifier.equals("foo"));
    }

    /**
     * Test equals when issuer fields are not equal.
     */
    @Test
    public void testEqualsDifferentIssuer() {
        OidcIdentifier identifierOne = new OidcIdentifier(ISSUER_ONE, SUBJECT_ONE);
        OidcIdentifier identifierTwo = new OidcIdentifier(ISSUER_TWO, SUBJECT_ONE);

        Assert.assertFalse(identifierOne.equals(identifierTwo));
    }

    /**
     * Test equals when the issuer fields are equal but subject fields are not equal.
     */
    @Test
    public void testEqualsDifferentScopedAffiliation() {
        OidcIdentifier identifierOne = new OidcIdentifier(ISSUER_ONE, SUBJECT_ONE);
        OidcIdentifier identifierTwo = new OidcIdentifier(ISSUER_ONE, SUBJECT_TWO);

        Assert.assertFalse(identifierOne.equals(identifierTwo));
    }

    /**
     * Test equals when objects are equal.
     */
    @Test
    public void testEquals() {
        OidcIdentifier identifierOne = new OidcIdentifier(ISSUER_ONE, SUBJECT_ONE);
        OidcIdentifier identifierTwo = new OidcIdentifier(ISSUER_ONE, SUBJECT_ONE);

        Assert.assertTrue(identifierOne.equals(identifierTwo));
    }

    /**
     * Test hashcode.
     */
    @Test
    public void testHashCode() {
        OidcIdentifier identifierOne = new OidcIdentifier(ISSUER_ONE, SUBJECT_ONE);
        OidcIdentifier identifierTwo = new OidcIdentifier(ISSUER_ONE, SUBJECT_ONE);

        Assert.assertEquals(identifierOne.hashCode(), identifierTwo.hashCode());
    }

    /**
     * Test compareTo when the objects  are equal.
     */
    @Test
    public void testCompareToEqual() {
        OidcIdentifier identifierOne = new OidcIdentifier(ISSUER_ONE, SUBJECT_ONE);
        OidcIdentifier identifierTwo = new OidcIdentifier(ISSUER_ONE, SUBJECT_ONE);

        Assert.assertTrue(identifierOne.compareTo(identifierTwo) == 0);
    }

    /**
     * Test compareTo when issuer fields are not equal.
     */
    @Test
    public void testCompareToDifferentIssuer() {
        OidcIdentifier identifierOne = new OidcIdentifier(ISSUER_ONE, SUBJECT_ONE);
        OidcIdentifier identifierTwo = new OidcIdentifier(ISSUER_TWO, SUBJECT_ONE);

        Assert.assertTrue(identifierOne.compareTo(identifierTwo) < 0);
    }

    /**
     * Test compareTo when subject fields are not equal.
     */
    @Test
    public void testCompareToDifferentSubject() {
        OidcIdentifier identifierOne = new OidcIdentifier(ISSUER_ONE, SUBJECT_ONE);
        OidcIdentifier identifierTwo = new OidcIdentifier(ISSUER_ONE, SUBJECT_TWO);

        Assert.assertTrue(identifierOne.compareTo(identifierTwo) < 0);
    }
}
