package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for NewSubscriberCommandDto.
 */
public class NewSubscriberCommandDtoTest {

    private static final String SUBSCRIBER_NAME = "Trevor Horn";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        NewSubscriberCommandDto commandDto = new NewSubscriberCommandDto(SUBSCRIBER_NAME);

        Assert.assertEquals(commandDto.getSubscriberName(), SUBSCRIBER_NAME);
    }

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        NewSubscriberCommandDto commandDto = new NewSubscriberCommandDto();

        Assert.assertNotNull(commandDto);
    }
}
