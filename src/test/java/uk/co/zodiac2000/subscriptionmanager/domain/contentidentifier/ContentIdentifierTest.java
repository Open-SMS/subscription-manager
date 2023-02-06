package uk.co.zodiac2000.subscriptionmanager.domain.contentidentifier;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for ContentIdentifier.
 */
public class ContentIdentifierTest {

    private static final long SUBSCRIPTION_RESOURCE_ID = 42L;

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        ContentIdentifier contentIdentifier = new ContentIdentifier(SUBSCRIPTION_RESOURCE_ID);

        Assert.assertNull(contentIdentifier.getId());
        Assert.assertEquals(contentIdentifier.getSubscriptionResourceId(), SUBSCRIPTION_RESOURCE_ID);
    }

    /**
     * Test zero-args constructor.
     */
    @Test
    public void testZeroArgsConstructor() {
        ContentIdentifier contentIdentifier = new ContentIdentifier();

        Assert.assertNotNull(contentIdentifier);
    }
}
