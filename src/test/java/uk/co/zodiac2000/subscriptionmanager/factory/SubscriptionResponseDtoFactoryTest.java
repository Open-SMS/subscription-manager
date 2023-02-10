package uk.co.zodiac2000.subscriptionmanager.factory;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.testng.MockitoTestNGListener;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription;
import uk.co.zodiac2000.subscriptionmanager.service.SubscriptionContentService;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionResponseDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.SubscriptionContentResponseDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceResponseDto;

/**
 * Unit tests for SubscriptionResponseDtoFactory.
 */
@Listeners(MockitoTestNGListener.class)
public class SubscriptionResponseDtoFactoryTest {

    private static final Clock CLOCK = Clock.fixed(Instant.parse("2012-06-03T10:15:30Z"), ZoneId.of("UTC"));
    private static final Long ID = 42L;
    private static final Optional<LocalDate> START_DATE = Optional.of(LocalDate.of(2012, 4, 1));
    private static final Optional<LocalDate> END_DATE = Optional.of(LocalDate.of(2012, 8, 23));
    private static final Long SUBSCRIPTION_CONTENT_ID = 293L;
    private static final String SUBSCRIPTION_CONTENT_DESCRIPTION = "Example Content";
    private static final Long SUBSCRIPTION_RESOURCE_ID = 303L;
    private static final String SUBSCRIPTION_RESOURCE_URI = "https://example.com";
    private static final String SUBSCRIPTION_RESOURCE_DESCRIPTION = "Example";
    private static final Long SUBSCRIBER_ID = 98L;
    private static final Long PERPETUAL_ID = 62L;
    private static final Long PERPETUAL_SUBSCRIBER_ID = 131L;
    private static final SubscriptionContentResponseDto SUBSCRIPTION_CONTENT_RESPONSE_DTO
            = new SubscriptionContentResponseDto(
                    SUBSCRIPTION_CONTENT_ID,
                    SUBSCRIPTION_CONTENT_DESCRIPTION,
                    new SubscriptionResourceResponseDto(SUBSCRIPTION_RESOURCE_ID, SUBSCRIPTION_RESOURCE_URI,
                            SUBSCRIPTION_RESOURCE_DESCRIPTION)
            );

    @Mock(lenient = true)
    private Clock fixedClock;

    @Mock(lenient = true)
    private SubscriptionContentService subscriptionContentService;

    @InjectMocks
    private SubscriptionResponseDtoFactory factory;

    private Subscription subscription;
    private Subscription perpetualSubscription;

    /**
     * Set up fixedClock mocked Clock object. The behaviour is based on the real Clock defined as a fixed clock;
     */
    @BeforeMethod
    public void setUpClock() {
        Mockito.when(this.fixedClock.instant()).thenReturn(CLOCK.instant());
        Mockito.when(this.fixedClock.getZone()).thenReturn(CLOCK.getZone());
    }

    /**
     * Set up subscriptionContentService mocked SubscriptionContentService object.
     */
    @BeforeMethod
    public void setUpSubscriptionContentService() {
        Mockito.when(this.subscriptionContentService.getSubscriptionContent(SUBSCRIPTION_CONTENT_ID))
                .thenReturn(Optional.of(SUBSCRIPTION_CONTENT_RESPONSE_DTO));
    }

    /**
     * Set up Subscription object.
     */
    @BeforeMethod
    public void setUpSubscription() {
        this.subscription  = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(this.subscription, "id", ID);

        this.perpetualSubscription = new Subscription(Optional.empty(), Optional.empty(),
                SUBSCRIPTION_CONTENT_ID, PERPETUAL_SUBSCRIBER_ID);
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
                        hasProperty("suspended", is(false)),
                        hasProperty("subscriptionContent", allOf(
                                hasProperty("id", is(SUBSCRIPTION_CONTENT_ID)),
                                hasProperty("contentDescription", is(SUBSCRIPTION_CONTENT_DESCRIPTION)),
                                hasProperty("subscriptionResource", allOf(
                                        hasProperty("id", is(SUBSCRIPTION_RESOURCE_ID)),
                                        hasProperty("resourceUri", is(SUBSCRIPTION_RESOURCE_URI)),
                                        hasProperty("resourceDescription", is(SUBSCRIPTION_RESOURCE_DESCRIPTION))
                                ))
                        )),
                        hasProperty("subscriberId", is(SUBSCRIBER_ID)),
                        hasProperty("active", is(true)),
                        hasProperty("canBeSuspended", is(true)),
                        hasProperty("canBeTerminated", is(true)),
                        hasProperty("canBeUnsuspended", is(false))
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
                        hasProperty("suspended", is(false)),
                        hasProperty("subscriptionContent", allOf(
                                hasProperty("id", is(SUBSCRIPTION_CONTENT_ID)),
                                hasProperty("contentDescription", is(SUBSCRIPTION_CONTENT_DESCRIPTION)),
                                hasProperty("subscriptionResource", allOf(
                                        hasProperty("id", is(SUBSCRIPTION_RESOURCE_ID)),
                                        hasProperty("resourceUri", is(SUBSCRIPTION_RESOURCE_URI)),
                                        hasProperty("resourceDescription", is(SUBSCRIPTION_RESOURCE_DESCRIPTION))
                                ))
                        )),
                        hasProperty("subscriberId", is(PERPETUAL_SUBSCRIBER_ID)),
                        hasProperty("active", is(true)),
                        hasProperty("canBeSuspended", is(true)),
                        hasProperty("canBeTerminated", is(true)),
                        hasProperty("canBeUnsuspended", is(false))
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
