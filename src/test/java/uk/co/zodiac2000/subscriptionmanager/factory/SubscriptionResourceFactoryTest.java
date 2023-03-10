package uk.co.zodiac2000.subscriptionmanager.factory;

import java.net.URI;
import org.testng.Assert;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptionresource.SubscriptionResource;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.NewSubscriptionResourceCommandDto;

/**
 * Unit tests for SubscriptionResourceFactory.
 */
public class SubscriptionResourceFactoryTest {

    private static final URI RESOURCE_URI = URI.create("https://example.com");
    private static final String RESOURCE_DESCRIPTION = "Example";

    private final SubscriptionResourceFactory factory = new SubscriptionResourceFactory();

    /**
     * Test newSubscriptionResourceCommandDtoToSubscriptionResource.
     */
    @Test
    public void testNewSubscriptionResourceCommandDtoToSubscriptionResource() {
        NewSubscriptionResourceCommandDto commandDto
                = new NewSubscriptionResourceCommandDto(RESOURCE_URI.toString(), RESOURCE_DESCRIPTION);
        SubscriptionResource subscriptionResource
                = this.factory.newSubscriptionResourceCommandDtoToSubscriptionResource(commandDto);

        Assert.assertNull(subscriptionResource.getId());
        Assert.assertEquals(subscriptionResource.getResourceUri(), RESOURCE_URI);
        Assert.assertEquals(subscriptionResource.getResourceDescription(), RESOURCE_DESCRIPTION);
    }
}
