package uk.co.zodiac2000.subscriptionmanager.factory;

import org.testng.Assert;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.Subscriber;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.NewSubscriberCommandDto;

/**
 * Unit tests for SubscriberFactory.
 */
public class SubscriberFactoryTest {

    private static final String SUBSCRIBER_NAME = "Terry Riley";

    private final SubscriberFactory subscriberFactory = new SubscriberFactory();

    /**
     * Test newSubscriberCommandDtoToSubscriber.
     */
    @Test
    public void testNewSubscriberCommandDtoToSubscriber() {
        Subscriber subscriber = this.subscriberFactory.newSubscriberCommandDtoToSubscriber(new NewSubscriberCommandDto(SUBSCRIBER_NAME));

        Assert.assertEquals(subscriber.getSubscriberName(), SUBSCRIBER_NAME);
    }
}
