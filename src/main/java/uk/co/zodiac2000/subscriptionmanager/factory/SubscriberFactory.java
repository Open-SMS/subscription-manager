package uk.co.zodiac2000.subscriptionmanager.factory;

import org.springframework.stereotype.Component;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.Subscriber;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.NewSubscriberCommandDto;

/**
 * Factory responsible for creating Subscriber aggregate roots.
 */
@Component
public class SubscriberFactory {

    /**
     * Returns a new configured Subscriber aggregate.
     * @param newSubscriberCommandDto command DTO representing the state of the new subscriber
     * @return a Subscriber
     */
    public Subscriber newSubscriberCommandDtoToSubscriber(final NewSubscriberCommandDto newSubscriberCommandDto) {
        return new Subscriber(newSubscriberCommandDto.getSubscriberName());
    }
}
