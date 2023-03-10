package uk.co.zodiac2000.subscriptionmanager.transfer.subscription;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for SubscriptionContentIdCommandDto.
 */
public class SubscriptionContentIdCommandDtoTest {

    private static final String SUBSCRIPTION_CONTENT_ID = "198";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        SubscriptionContentIdCommandDto commandDto = new SubscriptionContentIdCommandDto(SUBSCRIPTION_CONTENT_ID);

        Assert.assertEquals(commandDto.getSubscriptionContentId(), SUBSCRIPTION_CONTENT_ID);
    }

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        SubscriptionContentIdCommandDto commandDto = new SubscriptionContentIdCommandDto();

        Assert.assertNotNull(commandDto);
    }
}
