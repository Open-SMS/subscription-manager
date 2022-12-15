package uk.co.zodiac2000.subscriptionmanager.factory;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionResponseDto;

/**
 * Unit tests for SubscriptionResponseDtoFactory.
 */
public class SubscriptionResponseDtoFactoryTest {

    private static final Clock CLOCK = Clock.fixed(Instant.parse("2012-06-03T10:15:30Z"), ZoneId.of("UTC"));
    private static final Long ID = 42L;
    private static final Optional<LocalDate> START_DATE = Optional.of(LocalDate.of(2012, 4, 1));
    private static final Optional<LocalDate> END_DATE = Optional.of(LocalDate.of(2012, 8, 23));
    private static final String CONTENT_IDENTIFIER = "CONTENT";
    private static final Long SUBSCRIBER_ID = 98L;
    private static final Long PERPETUAL_ID = 62L;
    private static final String PERPETUAL_CONTENT_IDENTIFIER = "CONTENT-2";
    private static final Long PERPETUAL_SUBSCRIBER_ID = 131L;

    private final SubscriptionResponseDtoFactory factory = new SubscriptionResponseDtoFactory(CLOCK);

    private Subscription subscription;
    private Subscription perpetualSubscription;

    /**
     * Set up Subscription object.
     */
    @BeforeMethod
    public void setUpSubscription() {
        this.subscription  = new Subscription(START_DATE, END_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(this.subscription, "id", ID);

        this.perpetualSubscription = new Subscription(Optional.empty(), Optional.empty(), PERPETUAL_CONTENT_IDENTIFIER,
                PERPETUAL_SUBSCRIBER_ID);
        ReflectionTestUtils.setField(this.perpetualSubscription, "id", PERPETUAL_ID);
    }

    /**
     * Test subscriptionToSubscriptionResponseDto.
     */
    @Test
    public void testSubscriptionToSubscriptionResponseDto() {
        Optional<SubscriptionResponseDto> responseDto
                = this.factory.subscriptionToSubscriptionResponseDto(Optional.of(this.subscription));

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), is(
                allOf(
                        hasProperty("id", is(ID)),
                        hasProperty("startDate", is(START_DATE)),
                        hasProperty("endDate", is(END_DATE)),
                        hasProperty("terminated", is(false)),
                        hasProperty("contentIdentifier", is(CONTENT_IDENTIFIER)),
                        hasProperty("subscriberId", is(SUBSCRIBER_ID)),
                        hasProperty("active", is(true))
                )
        ));
    }

    /**
     * Test subscriptionToSubscriptionResponseDto when the startDate and endDate properties of the subscription are
     * empty.
     */
    @Test
    public void testSubscriptionToSubscriptionResponseDtoEmptyDates() {
        Optional<SubscriptionResponseDto> responseDto
                = this.factory.subscriptionToSubscriptionResponseDto(Optional.of(this.perpetualSubscription));

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), is(
                allOf(
                        hasProperty("id", is(PERPETUAL_ID)),
                        hasProperty("startDate", is(Optional.empty())),
                        hasProperty("endDate", is(Optional.empty())),
                        hasProperty("terminated", is(false)),
                        hasProperty("contentIdentifier", is(PERPETUAL_CONTENT_IDENTIFIER)),
                        hasProperty("subscriberId", is(PERPETUAL_SUBSCRIBER_ID)),
                        hasProperty("active", is(true))
                )
        ));
    }

    /**
     * Test subscriptionToSubscriptionResponseDto when the optional argument is empty.
     */
    @Test
    public void testSubscriptionToSubscriptionResponseDtoEmpty() {
        Optional<SubscriptionResponseDto> responseDto
                = this.factory.subscriptionToSubscriptionResponseDto(Optional.empty());

        Assert.assertTrue(responseDto.isEmpty());
    }
}
