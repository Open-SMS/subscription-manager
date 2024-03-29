package uk.co.zodiac2000.subscriptionmanager.factory;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription;
import uk.co.zodiac2000.subscriptionmanager.service.SubscriptionContentService;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionResponseDto;

/**
 * Factory that produces subscription response DTOs.
 */
@Component
public class SubscriptionResponseDtoFactory {

    /**
     * This clock is used in all date-time expressions that involve the current system date-time. It is autowired by
     * Spring using a bean available from the Spring context. In unit tests a Clock needs to be supplied, possibly a
     * fixed implementation. Integration tests can make use of the fixed clock bean produced by test class
     * TestClockConfiguration#fixedClock.
     */
    @Autowired
    private Clock systemClock;

    @Autowired
    private SubscriptionContentService subscriptionContentService;

    /**
     * Returns a SubscriptionResponseDto object with state based on the subscription argument.Dates are expressed as
     * Strings in ISO-8601 format uuuu-MM-dd. If the optional is empty then an empty optional is returned.
     *
     * @param subscription an optional Subscription
     * @return an optional response DTO
     */
    public Optional<SubscriptionResponseDto> subscriptionToSubscriptionResponseDto(
            final Optional<Subscription> subscription) {
        return subscription
                .map(s -> new SubscriptionResponseDto(
                        s.getId(),
                        s.getStartDate(),
                        s.getEndDate(),
                        s.isTerminated(), s.isSuspended(),
                        this.subscriptionContentService.getSubscriptionContent(s.getSubscriptionContentId()).get(),
                        s.getSubscriberId(),
                        s.isActive(LocalDate.now(this.systemClock)),
                        s.canBeSuspended(), s.canBeTerminated(), s.canBeUnsuspended()
                ));
    }
}
