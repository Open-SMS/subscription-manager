package uk.co.zodiac2000.subscriptionmanager.domain.subscriptionresource;

import java.net.URI;
import org.testng.Assert;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.UpdateSubscriptionResourceCommandDto;

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
    @Test
    public void testUpdateSubscriptionResource() {
        SubscriptionResource resource = new SubscriptionResource(RESOURCE_URI, RESOURCE_DESCRIPTION);
        resource.updateSubscriptionResource(new UpdateSubscriptionResourceCommandDto(42L, "http://foo.com", "Foo"));

        Assert.assertEquals(resource.getResourceUri(), URI.create("http://foo.com"));
        Assert.assertEquals(resource.getResourceDescription(), "Foo");
    }

    /**
     * Test constructor when resourceUri is null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorResourceUriNull() {
        SubscriptionResource resource = new SubscriptionResource(null, RESOURCE_DESCRIPTION);
    }

    /**
     * Test constructor when resourceDescription is null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorResourceDescriptionNull() {
        SubscriptionResource resource = new SubscriptionResource(RESOURCE_URI, null);
    }
}
