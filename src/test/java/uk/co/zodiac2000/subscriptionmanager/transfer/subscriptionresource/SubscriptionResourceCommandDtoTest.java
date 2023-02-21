package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for SubscriptionResourceCommandDto.
 */
public class SubscriptionResourceCommandDtoTest {

    private static final long ID = 42L;
    private static final String RESOURCE_URI = "https://example.com";
    private static final String RESOURCE_DESCRIPTION = "Example";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        SubscriptionResourceCommandDto commandDto
                = new SubscriptionResourceCommandDto(ID, RESOURCE_URI, RESOURCE_DESCRIPTION);

        Assert.assertEquals(commandDto.getId(), ID);
        Assert.assertEquals(commandDto.getResourceUri(), RESOURCE_URI);
        Assert.assertEquals(commandDto.getResourceDescription(), RESOURCE_DESCRIPTION);
    }

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        SubscriptionResourceCommandDto commandDto = new SubscriptionResourceCommandDto();

        Assert.assertNotNull(commandDto);
    }
}
