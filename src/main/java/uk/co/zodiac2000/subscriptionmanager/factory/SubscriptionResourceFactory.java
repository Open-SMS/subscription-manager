package uk.co.zodiac2000.subscriptionmanager.factory;

import java.net.URI;
import org.springframework.stereotype.Component;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptionresource.SubscriptionResource;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.NewSubscriptionResourceCommandDto;

/**
 * Factory responsible for creating SubscriptionResource aggregate roots.
 */
@Component
public class SubscriptionResourceFactory {

    /**
     * Returns a new configured SubscriptionResource aggregate.
     * @param newSubscriptionResourceCommandDto command DTO representing the state of the new subscription resource
     * @return a new SubscriptionResource
     */
    public SubscriptionResource newSubscriptionResourceCommandDtoToSubscriptionResource(
            final NewSubscriptionResourceCommandDto newSubscriptionResourceCommandDto) {
        return new SubscriptionResource(
                URI.create(newSubscriptionResourceCommandDto.getResourceUri()),
                newSubscriptionResourceCommandDto.getResourceDescription()
        );
    }

}
