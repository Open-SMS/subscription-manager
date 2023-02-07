package uk.co.zodiac2000.subscriptionmanager.factory;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.zodiac2000.subscriptionmanager.domain.contentidentifier.ContentIdentifier;
import uk.co.zodiac2000.subscriptionmanager.service.SubscriptionResourceService;
import uk.co.zodiac2000.subscriptionmanager.transfer.contentidentifier.ContentIdentifierResponseDto;

/**
 * Factory responsible for producing ContentIdentifierResponseDto objects.
 */
@Component
public class ContentIdentifierResponseDtoFactory {

    @Autowired
    private SubscriptionResourceService subscriptionResourceService;

    /**
     * Returns a ContentIdentifierResponseDto object with state based on the {@code contentIdentifier} argument. If
     * the argument is empty then an empty optional is returned.
     * @param contentIdentifier the content identifier
     * @return a ContentIdentifierResponseDto object
     */
    public Optional<ContentIdentifierResponseDto> contentIdentifierToResponseDto(
            final Optional<ContentIdentifier> contentIdentifier) {
        return contentIdentifier
                .map(c -> new ContentIdentifierResponseDto(
                        c.getId(),
                        this.subscriptionResourceService.getSubscriptionResource(c.getSubscriptionResourceId()).get()
                ));
    }
}
