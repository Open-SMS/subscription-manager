package uk.co.zodiac2000.subscriptionmanager.factory;

import java.time.LocalDate;
import java.util.Optional;
import org.testng.Assert;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.NewSubscriptionCommandDto;

/**
 * Unit tests for SubscriptionFactory.
 */
public class SubscriptionFactoryTest {

    private static final String START_DATE_STRING = "2021-02-03";
    private static final String END_DATE_STRING = "2022-02-03";
    private static final Optional<LocalDate> START_DATE = Optional.of(LocalDate.parse(START_DATE_STRING));
    private static final Optional<LocalDate> END_DATE = Optional.of(LocalDate.parse(END_DATE_STRING));
    private static final String SUBSCRIPTION_CONTENT_ID = "293";
    private static final String SUBSCRIBER_ID = "142";

    private final SubscriptionFactory factory = new SubscriptionFactory();

    /**
     * Test subscriptionCommandDtoToSubscription.
     */
    @Test
    public void testSubscriptionCommandDtoToSubscription() {
        NewSubscriptionCommandDto commandDto = new NewSubscriptionCommandDto(
                Optional.of(START_DATE_STRING), Optional.of(END_DATE_STRING), SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        Subscription subscription = this.factory.subscriptionCommandDtoToSubscription(commandDto);

       Assert.assertEquals(subscription.getStartDate(), START_DATE);
        Assert.assertEquals(subscription.getEndDate(), END_DATE);
        Assert.assertEquals(subscription.getSubscriptionContentId(), Long.valueOf(SUBSCRIPTION_CONTENT_ID));
        Assert.assertEquals(subscription.getSubscriberId(), Long.valueOf(SUBSCRIBER_ID));
    }

    /**
     * Test subscriptionCommandDtoToSubscription when the start and end  dates are empty strings.
     */
    @Test
    public void testSubscriptionCommandDtoToSubscriptionEmptyStartAndEnd() {
        NewSubscriptionCommandDto commandDto = new NewSubscriptionCommandDto(Optional.empty(), Optional.empty(),
                SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        Subscription subscription = this.factory.subscriptionCommandDtoToSubscription(commandDto);

        Assert.assertTrue(subscription.getStartDate().isEmpty());
        Assert.assertTrue(subscription.getEndDate().isEmpty());
    }
}
