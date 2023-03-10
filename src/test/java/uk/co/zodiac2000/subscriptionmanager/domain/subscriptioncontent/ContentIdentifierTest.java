package uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for ContentIdentifier.
 */
public class ContentIdentifierTest {

    private static final String CONTENT_IDENTIFIER_ONE = "TEST-CONTENT-ONE";
    private static final String CONTENT_IDENTIFIER_TWO = "TEST-CONTENT-TWO";

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        ContentIdentifier contentIdentifier = new ContentIdentifier();

        Assert.assertNotNull(contentIdentifier);
    }

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        ContentIdentifier contentIdentifier = new ContentIdentifier(CONTENT_IDENTIFIER_ONE);

        Assert.assertEquals(contentIdentifier.getContentIdentifier(), CONTENT_IDENTIFIER_ONE);
    }

    /**
     * Test constructor when argument is null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorNull() {
        ContentIdentifier contentIdentifier = new ContentIdentifier(null);
    }

    /**
     * Test equals when argument is null.
     */
    @Test
    public void testEqualsNull() {
        ContentIdentifier contentIdentifier = new ContentIdentifier(CONTENT_IDENTIFIER_ONE);

        Assert.assertFalse(contentIdentifier.equals(null));
    }

    /**
     * Test equals when argument is a different class.
     */
    @Test
    public void testEqualsDifferent() {
        ContentIdentifier contentIdentifier = new ContentIdentifier(CONTENT_IDENTIFIER_ONE);

        Assert.assertFalse(contentIdentifier.equals("foo"));
    }

    /**
     * Test equals when the contentIdentifier fields are not equal.
     */
    @Test
    public void testEqualsDifferentContentIdentifier() {
        ContentIdentifier contentIdentifierOne = new ContentIdentifier(CONTENT_IDENTIFIER_ONE);
        ContentIdentifier contentIdentifierTwo = new ContentIdentifier(CONTENT_IDENTIFIER_TWO);

        Assert.assertFalse(contentIdentifierOne.equals(contentIdentifierTwo));
    }

    /**
     * Test equals when the objects are equal.
     */
    @Test
    public void testEquals() {
        ContentIdentifier contentIdentifierOne = new ContentIdentifier(CONTENT_IDENTIFIER_ONE);
        ContentIdentifier contentIdentifierTwo = new ContentIdentifier(CONTENT_IDENTIFIER_ONE);

        Assert.assertTrue(contentIdentifierOne.equals(contentIdentifierTwo));
    }

    /**
     * Test hashcode.
     */
    @Test
    public void testHashCode() {
        ContentIdentifier contentIdentifierOne = new ContentIdentifier(CONTENT_IDENTIFIER_ONE);
        ContentIdentifier contentIdentifierTwo = new ContentIdentifier(CONTENT_IDENTIFIER_ONE);

        Assert.assertEquals(contentIdentifierOne.hashCode(), contentIdentifierTwo.hashCode());
    }

    /**
     * Test compareTo when the objects  are equal.
     */
    @Test
    public void testCompareToEqual() {
        ContentIdentifier contentIdentifierOne = new ContentIdentifier(CONTENT_IDENTIFIER_ONE);
        ContentIdentifier contentIdentifierTwo = new ContentIdentifier(CONTENT_IDENTIFIER_ONE);

        Assert.assertEquals(contentIdentifierOne.compareTo(contentIdentifierTwo), 0);
    }

    /**
     * Test compareTo when the objects are not equal.
     */
    @Test
    public void testCompareToNotEqual() {
        ContentIdentifier contentIdentifierOne = new ContentIdentifier(CONTENT_IDENTIFIER_ONE);
        ContentIdentifier contentIdentifierTwo = new ContentIdentifier(CONTENT_IDENTIFIER_TWO);

        Assert.assertTrue(contentIdentifierOne.compareTo(contentIdentifierTwo) < 0);
    }
}
