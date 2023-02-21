package uk.co.zodiac2000.subscriptionmanager.domain.subscriptionresource;

import java.net.URI;
import org.testng.Assert;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceCommandDto;

/**
 * Unit tests for SubscriptionResource.
 */
public class SubscriptionResourceTest {

    private static final URI RESOURCE_URI = URI.create("https://example.com");
    private static final String RESOURCE_DESCRIPTION = "Example";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        SubscriptionResource resource = new SubscriptionResource(RESOURCE_URI, RESOURCE_DESCRIPTION);

        Assert.assertNull(resource.getId());
        Assert.assertEquals(resource.getResourceUri(), RESOURCE_URI);
        Assert.assertEquals(resource.getResourceDescription(), RESOURCE_DESCRIPTION);
    }

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        SubscriptionResource resource = new SubscriptionResource();

        Assert.assertNotNull(resource);
    }

    /**
     * Test updateSubscriptionResource.
     */
    public void testUpdateSubscriptionResource() {
        SubscriptionResource resource = new SubscriptionResource(RESOURCE_URI, RESOURCE_DESCRIPTION);
        resource.updateSubscriptionResource(new SubscriptionResourceCommandDto(42L, "http://foo.com", "Foo"));

        Assert.assertEquals(resource.getResourceUri(), URI.create("http://foo.com"));
        Assert.assertEquals(resource.getResourceDescription(), "Foo");
    }
}
