package uk.co.zodiac2000.subscriptionmanager.domain.subscription;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionContentIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionDatesCommandDto;

/**
 * Unit tests for Subscription.
 */
public class SubscriptionTest {

    private static final Long ID = 42L;
    private static final Optional<LocalDate> START_DATE = Optional.of(LocalDate.of(2012, 4, 1));
    private static final Optional<LocalDate> END_DATE = Optional.of(LocalDate.of(2012, 8, 23));
    private static final String CONTENT_IDENTIFIER = "CONTENT";
    private static final Long SUBSCRIBER_ID = 98L;

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        Subscription subscription = new Subscription();

        Assert.assertNotNull(subscription);
    }

    /**
     * Test parameter constructor and accessors.
     */
    @Test
    public void testConstructor() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(subscription, "id", ID);

        Assert.assertEquals(subscription.getId(), ID);
        Assert.assertEquals(subscription.getStartDate(), START_DATE);
        Assert.assertEquals(subscription.getEndDate(), END_DATE);
        Assert.assertFalse(subscription.isTerminated());
        Assert.assertFalse(subscription.isSuspended());
        Assert.assertEquals(subscription.getContentIdentifier(), CONTENT_IDENTIFIER);
        Assert.assertEquals(subscription.getSubscriberId(), SUBSCRIBER_ID);
    }

    /**
     * Test parameter constructor when end date equals start date.
     */
    @Test
    public void testConstructorEndEqualsStart() {
        Subscription subscription = new Subscription(START_DATE, START_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);

        Assert.assertEquals(subscription.getStartDate(), START_DATE);
        Assert.assertEquals(subscription.getEndDate(), START_DATE);
    }

    /**
     * Test parameter constructor when end date is before start date.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testConstructorEndBeforeStart() {
        Subscription subscription = new Subscription(END_DATE, START_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);
    }

    /**
     * Test terminate.
     */
    @Test
    public void testTerminate() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);
        subscription.terminate();

        Assert.assertTrue(subscription.isTerminated());
        Assert.assertTrue(subscription.isSuspended());
    }

    /**
     * Test isActive when the subscription is suspended.
     */
    @Test
    public void testIsActiveTerminated() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(subscription, "suspended", true);

        Assert.assertFalse(subscription.isActive(START_DATE.get().plusDays(1)));
    }

    /**
     * Test isActive when atDate is before the subscription startDate.
     */
    @Test
    public void testIsActiveBeforeStart() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);

        Assert.assertFalse(subscription.isActive(START_DATE.get().minusDays(1)));
    }

    /**
     * Test isActive when atDate is equal to the subscription startDate.
     */
    @Test
    public void testIsActiveBeforeAtStart() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);

        Assert.assertTrue(subscription.isActive(START_DATE.get()));
    }

    /**
     * Test isActive when atDate is between the subscription startDate and endDate.
     */
    @Test
    public void testIsActive() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);

        Assert.assertTrue(subscription.isActive(START_DATE.get().plusDays(1)));
    }

    /**
     * Test isActive when atDate is equal to the subscription endDate.
     */
    @Test
    public void testIsActiveBeforeAtEnd() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);

        Assert.assertTrue(subscription.isActive(END_DATE.get()));
    }

    /**
     * Test isActive when atDate is after the subscription endDate.
     */
    @Test
    public void testIsActiveAfterEnd() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);

        Assert.assertFalse(subscription.isActive(END_DATE.get().plusDays(1)));
    }

    /**
     * Test isActive when atDate is before endDate and startDate is empty.
     */
    @Test
    public void testIsActiveEmptyStart() {
        Subscription subscription = new Subscription(Optional.empty(), END_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);

        Assert.assertTrue(subscription.isActive(END_DATE.get().minusYears(5)));
    }

    /**
     * Test isActive when atDate is before endDate and startDate is empty.
     */
    @Test
    public void testIsActiveEmptyEnd() {
        Subscription subscription = new Subscription(START_DATE, Optional.empty(), CONTENT_IDENTIFIER, SUBSCRIBER_ID);

        Assert.assertTrue(subscription.isActive(START_DATE.get().plusYears(5)));
    }

    /**
     * Test setDates when dates are empty Optional objects.
     */
    @Test
    public void setDatesEmpty() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);
        subscription.setDates(new SubscriptionDatesCommandDto(Optional.empty(), Optional.empty()));

        Assert.assertTrue(subscription.getStartDate().isEmpty());
        Assert.assertTrue(subscription.getEndDate().isEmpty());
    }

    /**
     * Test setDates when dates are updated dates.
     */
    @Test
    public void setDatesUpdated() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);
        subscription.setDates(new SubscriptionDatesCommandDto(Optional.of("2012-04-21"), Optional.of("2012-12-31")));

        Assert.assertEquals(subscription.getStartDate(), Optional.of(LocalDate.parse("2012-04-21")));
        Assert.assertEquals(subscription.getEndDate(), Optional.of(LocalDate.parse("2012-12-31")));
    }

    /**
     * Test setDates when dates are updated dates but the end date occurs before the start date.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void setDatesUpdatedInvalid() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);
        subscription.setDates(new SubscriptionDatesCommandDto(Optional.of("2013-04-21"), Optional.of("2012-12-31")));
    }

    /**
     * Test setContentIdentifier.
     */
    @Test
    public void testSetContentIdentifier() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);
        subscription.setContentIdentifier(new SubscriptionContentIdentifierCommandDto("CONTENT-X"));

        Assert.assertEquals(subscription.getContentIdentifier(), "CONTENT-X");
    }
}
