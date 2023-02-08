package uk.co.zodiac2000.subscriptionmanager.factory;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent.SubscriptionContent;
import uk.co.zodiac2000.subscriptionmanager.service.SubscriptionResourceService;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.SubscriptionContentResponseDto;

/**
 * Factory responsible for producing SubscriptionContentResponseDto objects.
 */
@Component
public class SubscriptionContentResponseDtoFactory {

    @Autowired
    private SubscriptionResourceService subscriptionResourceService;

    /**
     * Returns a SubscriptionContentResponseDto object with state based on the {@code subscriptionContent} argument. If
     * the argument is empty then an empty optional is returned.
     * @param subscriptionContent the subscription content
     * @return a SubscriptionContentResponseDto object
     */
    public Optional<SubscriptionContentResponseDto> subscriptionContentToResponseDto(
            final Optional<SubscriptionContent> subscriptionContent) {
        return subscriptionContent
                .map(c -> new SubscriptionContentResponseDto(
                        c.getId(),
                        this.subscriptionResourceService.getSubscriptionResource(c.getSubscriptionResourceId()).get()
                ));
    }
}
