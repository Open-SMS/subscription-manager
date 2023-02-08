package uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for SubscriptionContent.
 */
public class SubscriptionContentTest {

    private static final long SUBSCRIPTION_RESOURCE_ID = 42L;

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        SubscriptionContent contentIdentifier = new SubscriptionContent(SUBSCRIPTION_RESOURCE_ID);

        Assert.assertNull(contentIdentifier.getId());
        Assert.assertEquals(contentIdentifier.getSubscriptionResourceId(), SUBSCRIPTION_RESOURCE_ID);
    }

    /**
     * Test zero-args constructor.
     */
    @Test
    public void testZeroArgsConstructor() {
        SubscriptionContent contentIdentifier = new SubscriptionContent();

        Assert.assertNotNull(contentIdentifier);
    }
}
