package uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent;

import java.util.Set;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for SubscriptionContent.
 */
public class SubscriptionContentTest {

    private static final String SUBSCRIPTION_CONTENT_DESCRIPTION = "Example Content";
    private static final String CONTENT_IDENTIFIER = "CONTENT";
    private static final Set<ContentIdentifier> CONTENT_IDENTIFIERS = Set.of(
            new ContentIdentifier(CONTENT_IDENTIFIER)
    );
    private static final long SUBSCRIPTION_RESOURCE_ID = 42L;

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        SubscriptionContent contentIdentifier = new SubscriptionContent(SUBSCRIPTION_CONTENT_DESCRIPTION,
                CONTENT_IDENTIFIERS, SUBSCRIPTION_RESOURCE_ID);

        Assert.assertNull(contentIdentifier.getId());
        Assert.assertEquals(contentIdentifier.getContentDescription(), SUBSCRIPTION_CONTENT_DESCRIPTION);
        Assert.assertEquals(contentIdentifier.getContentIdentifiers(), CONTENT_IDENTIFIERS);
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

    /**
     * Test constructor when contentDescription is null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorContentDescriptionNull() {
        SubscriptionContent contentIdentifier = new SubscriptionContent(null,
                CONTENT_IDENTIFIERS, SUBSCRIPTION_RESOURCE_ID);
    }

    /**
     * Test constructor when contentIdentifiers are null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorContentIdentifiersNull() {
        SubscriptionContent contentIdentifier = new SubscriptionContent(SUBSCRIPTION_CONTENT_DESCRIPTION,
                null, SUBSCRIPTION_RESOURCE_ID);
    }

    /**
     * Test constructor when subscriptionResourceId is null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorSubscriptionResourceIdNull() {
        SubscriptionContent contentIdentifier = new SubscriptionContent(SUBSCRIPTION_CONTENT_DESCRIPTION,
                CONTENT_IDENTIFIERS, null);
    }
}
