package uk.co.zodiac2000.subscriptionmanager.factory;

import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptionresource.SubscriptionResource;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceResponseDto;

/**
 * Factory responsible for producing SubscriptionResourceResponseDto objects.
 */
@Component
public class SubscriptionResourceResponseDtoFactory {

    /**
     * Returns a SubscriptionResourceResponseDto with state based on the subscriptionResource argument, or an
     * empty optional if the argument is empty. The resourceUri URI is converted to a String representation.
     * @param subscriptionResource the subscription resource
     * @return a SubscriptionResourceResponseDto
     */
    public Optional<SubscriptionResourceResponseDto> subscriptionResourceToResponseDto(
            final Optional<SubscriptionResource> subscriptionResource) {
        return subscriptionResource
                .map(r -> new SubscriptionResourceResponseDto(r.getId(), r.getResourceUri().toString(),
                        r.getResourceDescription()));
    }
}
