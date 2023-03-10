package uk.co.zodiac2000.subscriptionmanager.domain.subscription;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionContentIdCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionDatesCommandDto;

/**
 * Unit tests for Subscription.
 */
public class SubscriptionTest {

    private static final Long ID = 42L;
    private static final Optional<LocalDate> START_DATE = Optional.of(LocalDate.of(2012, 4, 1));
    private static final Optional<LocalDate> END_DATE = Optional.of(LocalDate.of(2012, 8, 23));
    private static final Long SUBSCRIPTION_CONTENT_ID = 293L;
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
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(subscription, "id", ID);

        Assert.assertEquals(subscription.getId(), ID);
        Assert.assertEquals(subscription.getStartDate(), START_DATE);
        Assert.assertEquals(subscription.getEndDate(), END_DATE);
        Assert.assertFalse(subscription.isTerminated());
        Assert.assertFalse(subscription.isSuspended());
        Assert.assertEquals(subscription.getSubscriptionContentId(), SUBSCRIPTION_CONTENT_ID);
        Assert.assertEquals(subscription.getSubscriberId(), SUBSCRIBER_ID);
    }

    /**
     * Test parameter constructor when end date equals start date.
     */
    @Test
    public void testConstructorEndEqualsStart() {
        Subscription subscription = new Subscription(START_DATE, START_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);

        Assert.assertEquals(subscription.getStartDate(), START_DATE);
        Assert.assertEquals(subscription.getEndDate(), START_DATE);
    }

    /**
     * Test parameter constructor when end date is before start date.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testConstructorEndBeforeStart() {
        Subscription subscription = new Subscription(END_DATE, START_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
    }

    /**
     * Test terminate.
     */
    @Test
    public void testTerminate() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        subscription.terminate();

        Assert.assertTrue(subscription.isTerminated());
        Assert.assertTrue(subscription.isSuspended());
    }

    /**
     * Test terminate when the subscription is already terminated. This should result in an IllegalStateException
     * because an already terminated subscription cannot be terminated.
     */
    @Test
    public void testTerminateAlreadyTerminated() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(subscription, "suspended", true);
        ReflectionTestUtils.setField(subscription, "terminated", true);

        try {
            subscription.terminate();
        } catch (IllegalStateException e) {
            Assert.assertEquals(e.getMessage(),
                    "This subscription cannot be terminated because it is already terminated");
        }

        // Verify subscription state is unchanged.
        Assert.assertTrue(subscription.isTerminated());
        Assert.assertTrue(subscription.isSuspended());
    }

    /**
     * Test terminate when the subscription is already suspended. This should result in the subscription being
     * terminated because terminating a suspended subscription is allowed.
     */
    @Test
    public void testTerminateAlreadySuspended() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(subscription, "suspended", true);

        subscription.terminate();

        Assert.assertTrue(subscription.isTerminated());
        Assert.assertTrue(subscription.isSuspended());
    }

    /**
     * Test suspend.
     */
    @Test
    public void testSuspend() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);

        subscription.suspend();

        Assert.assertFalse(subscription.isTerminated());
        Assert.assertTrue(subscription.isSuspended());
    }

    /**
     * Test suspend when the subscription is already suspended.
     */
    @Test
    public void testSuspendAlreadySuspended() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(subscription, "suspended", true);

        try {
            subscription.suspend();
        } catch (IllegalStateException e) {
            Assert.assertEquals(e.getMessage(),
                    "This subscription cannot be suspended because it is already suspended");
        }

        // Verify subscription state is unchanged.
        Assert.assertFalse(subscription.isTerminated());
        Assert.assertTrue(subscription.isSuspended());
    }

    /**
     * Test unsuspend when the subscription is suspended.
     */
    @Test
    public void testUnsuspend() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(subscription, "suspended", true);

        subscription.unsuspend();

        Assert.assertFalse(subscription.isTerminated());
        Assert.assertFalse(subscription.isSuspended());
    }

    /**
     * Test unsuspend when the subscription is terminated.
     */
    @Test
    public void testUnsuspendTerminated() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(subscription, "suspended", true);
        ReflectionTestUtils.setField(subscription, "terminated", true);

        try {
            subscription.unsuspend();
            Assert.fail("IllegalStateException should be thrown");
        } catch (IllegalStateException e) {
            Assert.assertEquals(e.getMessage(), "This subscription cannot be unsuspended because it is terminated");
        }

        // Verify subscription state is unchanged.
        Assert.assertTrue(subscription.isTerminated());
        Assert.assertTrue(subscription.isSuspended());
    }

    /**
     * Test unsuspend when the subscription is not suspended.
     */
    @Test
    public void testUnsuspendNotSuspended() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);

        try {
            subscription.unsuspend();
            Assert.fail("IllegalStateException should be thrown");
        } catch (IllegalStateException e) {
            Assert.assertEquals(e.getMessage(), "This subscription cannot be unsuspended because it is not suspended");
        }

        // Verify subscription state is unchanged.
        Assert.assertFalse(subscription.isTerminated());
        Assert.assertFalse(subscription.isSuspended());
    }

    /**
     * Test isActive when the subscription is suspended.
     */
    @Test
    public void testIsActiveSuspended() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(subscription, "suspended", true);

        Assert.assertFalse(subscription.isActive(START_DATE.get().plusDays(1)));
    }

    /**
     * Test canBeTerminated when the subscription is terminated.
     */
    @Test
    public void testCanBeTerminated() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(subscription, "suspended", true);
        ReflectionTestUtils.setField(subscription, "terminated", true);

        Assert.assertFalse(subscription.canBeTerminated());
    }

    /**
     * Test canBeTerminated when the subscription is not terminated but is suspended.
     */
    @Test
    public void testCanBeTerminatedNotTerminated() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(subscription, "suspended", true);

        Assert.assertTrue(subscription.canBeTerminated());
    }

    /**
     * Test canBeSuspended when the subscription is not suspended.
     */
    @Test
    public void testCanBeSuspended() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);

        Assert.assertTrue(subscription.canBeSuspended());
    }

    /**
     * Test canBeSuspended when the subscription is suspended.
     */
    @Test
    public void testCanBeSuspendedIsSuspended() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(subscription, "suspended", true);

        Assert.assertFalse(subscription.canBeSuspended());
    }

    /**
     * Test canBeUnsuspended when the subscription is suspended but not terminated.
     */
    @Test
    public void testCanBeUnsuspended() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(subscription, "suspended", true);

        Assert.assertTrue(subscription.canBeUnsuspended());
    }

    /**
     * Test canBeUnsuspended when the subscription is terminated.
     */
    @Test
    public void testCanBeUnsuspendedTerminated() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        ReflectionTestUtils.setField(subscription, "suspended", true);
        ReflectionTestUtils.setField(subscription, "terminated", true);

        Assert.assertFalse(subscription.canBeUnsuspended());
    }

    /**
     * Test canBeUnsuspended when the subscription is not suspended.
     */
    @Test
    public void testCanBeUnsuspendedNotSuspended() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);

        Assert.assertFalse(subscription.canBeUnsuspended());
    }

    /**
     * Test isActive when atDate is before the subscription startDate.
     */
    @Test
    public void testIsActiveBeforeStart() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);

        Assert.assertFalse(subscription.isActive(START_DATE.get().minusDays(1)));
    }

    /**
     * Test isActive when atDate is equal to the subscription startDate.
     */
    @Test
    public void testIsActiveBeforeAtStart() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);

        Assert.assertTrue(subscription.isActive(START_DATE.get()));
    }

    /**
     * Test isActive when atDate is between the subscription startDate and endDate.
     */
    @Test
    public void testIsActive() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);

        Assert.assertTrue(subscription.isActive(START_DATE.get().plusDays(1)));
    }

    /**
     * Test isActive when atDate is equal to the subscription endDate.
     */
    @Test
    public void testIsActiveBeforeAtEnd() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);

        Assert.assertTrue(subscription.isActive(END_DATE.get()));
    }

    /**
     * Test isActive when atDate is after the subscription endDate.
     */
    @Test
    public void testIsActiveAfterEnd() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);

        Assert.assertFalse(subscription.isActive(END_DATE.get().plusDays(1)));
    }

    /**
     * Test isActive when atDate is before endDate and startDate is empty.
     */
    @Test
    public void testIsActiveEmptyStart() {
        Subscription subscription = new Subscription(Optional.empty(), END_DATE, SUBSCRIPTION_CONTENT_ID,
                SUBSCRIBER_ID);

        Assert.assertTrue(subscription.isActive(END_DATE.get().minusYears(5)));
    }

    /**
     * Test isActive when atDate is before endDate and startDate is empty.
     */
    @Test
    public void testIsActiveEmptyEnd() {
        Subscription subscription = new Subscription(START_DATE, Optional.empty(), SUBSCRIPTION_CONTENT_ID,
                SUBSCRIBER_ID);

        Assert.assertTrue(subscription.isActive(START_DATE.get().plusYears(5)));
    }

    /**
     * Test setDates when dates are empty Optional objects.
     */
    @Test
    public void setDatesEmpty() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        subscription.setDates(new SubscriptionDatesCommandDto(Optional.empty(), Optional.empty()));

        Assert.assertTrue(subscription.getStartDate().isEmpty());
        Assert.assertTrue(subscription.getEndDate().isEmpty());
    }

    /**
     * Test setDates when dates are updated dates.
     */
    @Test
    public void setDatesUpdated() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        subscription.setDates(new SubscriptionDatesCommandDto(Optional.of("2012-04-21"), Optional.of("2012-12-31")));

        Assert.assertEquals(subscription.getStartDate(), Optional.of(LocalDate.parse("2012-04-21")));
        Assert.assertEquals(subscription.getEndDate(), Optional.of(LocalDate.parse("2012-12-31")));
    }

    /**
     * Test setDates when dates are updated dates but the end date occurs before the start date.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void setDatesUpdatedInvalid() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        subscription.setDates(new SubscriptionDatesCommandDto(Optional.of("2013-04-21"), Optional.of("2012-12-31")));
    }

    /**
     * Test setSubscriptionContentId.
     */
    @Test
    public void testSetContentIdentifier() {
        Subscription subscription = new Subscription(START_DATE, END_DATE, SUBSCRIPTION_CONTENT_ID, SUBSCRIBER_ID);
        subscription.setSubscriptionContentId(new SubscriptionContentIdCommandDto("874"));

        Assert.assertEquals(subscription.getSubscriptionContentId(), 874L);
    }
}
