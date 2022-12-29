package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for SubscriberNameCommandDto.
 */
public class SubscriberNameCommandDtoTest {

    private static final Long SUBSCRIBER_ID = 65L;
    private static final String SUBSCRIBER_NAME = "Alan Davey";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        SubscriberNameCommandDto commandDto = new SubscriberNameCommandDto(SUBSCRIBER_ID, SUBSCRIBER_NAME);

        Assert.assertEquals(commandDto.getId(), SUBSCRIBER_ID);
        Assert.assertEquals(commandDto.getSubscriberName(), SUBSCRIBER_NAME);
    }

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        SubscriberNameCommandDto commandDto = new SubscriberNameCommandDto();

        Assert.assertNotNull(commandDto);
    }
}
