package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for NewSubscriptionResourceCommandDto.
 */
public class NewSubscriptionResourceCommandDtoTest {

    private static final String RESOURCE_URI = "https://example.com";
    private static final String RESOURCE_DESCRIPTION = "Example";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        NewSubscriptionResourceCommandDto commandDto
                = new NewSubscriptionResourceCommandDto(RESOURCE_URI, RESOURCE_DESCRIPTION);

        Assert.assertEquals(commandDto.getResourceUri(), RESOURCE_URI);
        Assert.assertEquals(commandDto.getResourceDescription(), RESOURCE_DESCRIPTION);
    }

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        NewSubscriptionResourceCommandDto commandDto = new NewSubscriptionResourceCommandDto();

        Assert.assertNotNull(commandDto);
    }
}
