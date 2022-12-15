package uk.co.zodiac2000.subscriptionmanager.factory;

import java.time.LocalDate;
import org.springframework.stereotype.Component;
import uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.NewSubscriptionCommandDto;

/**
 * Factory responsible for creating Subscription aggregate roots.
 */
@Component
public class SubscriptionFactory {

    /**
     * Return a new configured Subscription.
     * @param commandDto command DTO representing the state of the new subscription
     * @return a new Subscription
     */
    public Subscription subscriptionCommandDtoToSubscription(NewSubscriptionCommandDto commandDto) {
        return new Subscription(
                commandDto.getStartDate().map(LocalDate::parse),
                commandDto.getEndDate().map(LocalDate::parse),
                commandDto.getContentIdentifier(), Long.valueOf(commandDto.getSubscriberId())
        );
    }
}
