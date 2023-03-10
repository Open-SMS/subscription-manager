package uk.co.zodiac2000.subscriptionmanager.factory;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent.ContentIdentifier;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent.SubscriptionContent;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.NewSubscriptionContentCommandDto;

/**
 * Factory responsible for producing new subscription content aggregates.
 */
@Component
public class SubscriptionContentFactory {

    /**
     * Creates a new SubscriptionContent aggregate with state based on the commandDto argument.
     * @param commandDto command DTO representing the state of the new aggregate
     * @return a SubscriptionContent object
     */
    public SubscriptionContent  newSubscriptionContentCommandDtoToSubscriptionContent(
            final NewSubscriptionContentCommandDto commandDto) {
        Set<ContentIdentifier> contentIdentifiers = commandDto.getContentIdentifiers().stream()
                .map(c -> new ContentIdentifier(c.getContentIdentifier()))
                .collect(Collectors.toSet());
        return new SubscriptionContent(commandDto.getContentDescription(), contentIdentifiers,
                Long.valueOf(commandDto.getSubscriptionResourceId()));
    }
}
