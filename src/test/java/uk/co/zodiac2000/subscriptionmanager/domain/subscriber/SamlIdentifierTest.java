package uk.co.zodiac2000.subscriptionmanager.domain.subscriber;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for SamlIdentifier.
 */
public class SamlIdentifierTest {

    private static final String ENTITY_ID_ONE = "urn:mace:ac.uk:sdss.ac.uk:provider:identity:dur.ac.uk";
    private static final String ENTITY_ID_TWO = "https://idp.cardiff.ac.uk/shibboleth";
    private static final String SCOPED_AFFILIATION_ONE = "staff@cardiff.ac.uk";
    private static final String SCOPED_AFFILIATION_TWO = "member@dur.ac.uk";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        SamlIdentifier identifier = new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_ONE);

        Assert.assertEquals(identifier.getEntityId(), ENTITY_ID_ONE);
        Assert.assertEquals(identifier.getScopedAffiliation(),  SCOPED_AFFILIATION_ONE);
    }

    /**
     * Test constructor when entityId is null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorEntityIdNull() {
        SamlIdentifier identifier = new SamlIdentifier(null, SCOPED_AFFILIATION_ONE);
    }

    /**
     * Test constructor when scopedAffiliation is null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorScopedAffiliationNull() {
        SamlIdentifier identifier = new SamlIdentifier(ENTITY_ID_ONE, null);
    }

    /**
     * Test equals when argument is null.
     */
    @Test
    public void testEqualsNull() {
        SamlIdentifier identifier = new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_ONE);

        Assert.assertFalse(identifier.equals(null));
    }

    /**
     * Test equals when argument is a different class.
     */
    @Test
    public void testEqualsDifferent() {
        SamlIdentifier identifier = new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_ONE);

        Assert.assertFalse(identifier.equals("foo"));
    }

    /**
     * Test equals when entityId fields are not equal.
     */
    @Test
    public void testEqualsDifferentEntityId() {
        SamlIdentifier identifierOne = new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_ONE);
        SamlIdentifier identifierTwo = new SamlIdentifier(ENTITY_ID_TWO, SCOPED_AFFILIATION_ONE);

        Assert.assertFalse(identifierOne.equals(identifierTwo));
    }

    /**
     * Test equals when entityId fields are equal but scopedAffiliation fields are not equal.
     */
    @Test
    public void testEqualsDifferentScopedAffiliation() {
        SamlIdentifier identifierOne = new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_ONE);
        SamlIdentifier identifierTwo = new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_TWO);

        Assert.assertFalse(identifierOne.equals(identifierTwo));
    }

    /**
     * Test equals when objects are equal.
     */
    @Test
    public void testEquals() {
        SamlIdentifier identifierOne = new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_ONE);
        SamlIdentifier identifierTwo = new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_ONE);

        Assert.assertTrue(identifierOne.equals(identifierTwo));
    }

    /**
     * Test hashcode.
     */
    @Test
    public void testHashCode() {
        SamlIdentifier identifierOne = new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_ONE);
        SamlIdentifier identifierTwo = new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_ONE);

        Assert.assertEquals(identifierOne.hashCode(), identifierTwo.hashCode());
    }

    /**
     * Test compareTo when the objects  are equal.
     */
    @Test
    public void testCompareToEqual() {
        SamlIdentifier identifierOne = new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_ONE);
        SamlIdentifier identifierTwo = new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_ONE);

        Assert.assertTrue(identifierOne.compareTo(identifierTwo) == 0);
    }

    /**
     * Test compareTo when entityId fields are not equal.
     */
    @Test
    public void testCompareToDifferentEntityId() {
        SamlIdentifier identifierOne = new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_ONE);
        SamlIdentifier identifierTwo = new SamlIdentifier(ENTITY_ID_TWO, SCOPED_AFFILIATION_ONE);

        Assert.assertTrue(identifierOne.compareTo(identifierTwo) > 0);
    }

    /**
     * Test compareTo when scopedAffiliation fields are not equal.
     */
    @Test
    public void testCompareToDifferentScopedAffiliation() {
        SamlIdentifier identifierOne = new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_ONE);
        SamlIdentifier identifierTwo = new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_TWO);

        Assert.assertTrue(identifierOne.compareTo(identifierTwo) > 0);
    }
}
