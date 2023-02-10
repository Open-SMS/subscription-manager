package uk.co.zodiac2000.subscriptionmanager.service;

import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.EntityManager;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.configuration.TestClockConfiguration;
import uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription;
import uk.co.zodiac2000.subscriptionmanager.repository.SubscriptionRepository;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.NewSubscriptionCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionContentIdCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionDatesCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionResponseDto;

/**
 * Integration tests for SubscriptionService.
 */
@SpringBootTest(classes = {TestClockConfiguration.class})
@TestPropertySource(locations = "classpath:application-integration.properties")
public class SubscriptionServiceTestITCase extends AbstractTransactionalTestNGSpringContextTests {

    private static final Optional<String> START_DATE = Optional.of("2011-01-01");
    private static final Optional<String> END_DATE = Optional.of("2014-12-31");
    private static final Long SUBSCRIPTION_CONTENT_ID = 100000002L; // Universal Reference Music library
    private static final Long SUBSCRIBER_ID = 100000005L; // The Open University

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeMethod
    public void loadTestData() {
        executeSqlScript("classpath:test_data/claim_name_test_data.sql", false);
        executeSqlScript("classpath:test_data/subscriber_test_data.sql", false);
        executeSqlScript("classpath:test_data/subscription_resource_test_data.sql", false);
        executeSqlScript("classpath:test_data/subscription_content_test_data.sql", false);
        executeSqlScript("classpath:test_data/subscription_test_data.sql", false);
    }

    /**
     * Test getSubscription when the subscription exists and is active due to the subscription period containing the
     * test date, and the subscription not being terminated.
     */
    @Test
    public void testGetSubscription() {
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.getSubscription(100000001L);

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), is(
                allOf(
                        hasProperty("id", is(100000001L)),
                        hasProperty("startDate", is(Optional.of(LocalDate.parse("2012-01-01")))),
                        hasProperty("endDate", is(Optional.of(LocalDate.parse("2012-12-31")))),
                        hasProperty("terminated", is(false)),
                        hasProperty("suspended", is(false)),
                        hasProperty("subscriptionContent", allOf(
                                hasProperty("id", is(100000002L)),
                                hasProperty("subscriptionResource", allOf(
                                        hasProperty("id", is(100000002L)),
                                        hasProperty("resourceUri", is("https://universal-reference.com/music")),
                                        hasProperty("resourceDescription", is("Universal Reference Music library"))
                                ))
                        )),
                        hasProperty("subscriberId", is(100000001L)),
                        hasProperty("active", is(true)),
                        hasProperty("canBeSuspended", is(true)),
                        hasProperty("canBeTerminated", is(true)),
                        hasProperty("canBeUnsuspended", is(false))
                )
        ));
    }

    /**
     * Test getSubscription when the subscription does not exist.
     */
    @Test
    public void testGetSubscriptionNotExists() {
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.getSubscription(123L);

        Assert.assertTrue(responseDto.isEmpty());
    }

    /**
     * Test getSubscription when the subscription exists and is inactive due to being terminated.
     */
    @Test
    public void testGetSubscriptionTerminated() {
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.getSubscription(100000007L);

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), is(
                allOf(
                        hasProperty("id", is(100000007L)),
                        hasProperty("startDate", is(Optional.of(LocalDate.parse("2012-01-01")))),
                        hasProperty("endDate", is(Optional.of(LocalDate.parse("2012-12-31")))),
                        hasProperty("terminated", is(true)),
                        hasProperty("suspended", is(true)),
                        hasProperty("subscriptionContent", allOf(
                                hasProperty("id", is(100000001L)),
                                hasProperty("subscriptionResource", allOf(
                                        hasProperty("id", is(100000001L)),
                                        hasProperty("resourceUri", is("https://example.com")),
                                        hasProperty("resourceDescription", is("Example"))
                                ))
                        )),
                        hasProperty("subscriberId", is(100000008L)),
                        hasProperty("active", is(false)),
                        hasProperty("canBeSuspended", is(false)),
                        hasProperty("canBeTerminated", is(false)),
                        hasProperty("canBeUnsuspended", is(false))
                )
        ));
    }

    /**
     * Test getSubscription when the subscription exists and is inactive due to being suspended.
     */
    @Test
    public void testGetSubscriptionSuspended() {
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.getSubscription(100000014L);

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), is(
                allOf(
                        hasProperty("id", is(100000014L)),
                        hasProperty("startDate", is(Optional.of(LocalDate.parse("2012-01-01")))),
                        hasProperty("endDate", is(Optional.of(LocalDate.parse("2012-12-31")))),
                        hasProperty("terminated", is(false)),
                        hasProperty("suspended", is(true)),
                        hasProperty("subscriptionContent", allOf(
                                hasProperty("id", is(100000003L)),
                                hasProperty("subscriptionResource", allOf(
                                        hasProperty("id", is(100000003L)),
                                        hasProperty("resourceUri", is("https://universal-reference.com/economics")),
                                        hasProperty("resourceDescription", is("Universal Reference Economics library"))
                                ))
                        )),
                        hasProperty("subscriberId", is(100000010L)),
                        hasProperty("active", is(false)),
                        hasProperty("canBeSuspended", is(false)),
                        hasProperty("canBeTerminated", is(true)),
                        hasProperty("canBeUnsuspended", is(true))
                )
        ));
    }

    /**
     * Test getSubscription when the subscription exists and is inactive due to the subscription period not including
     * the test date.
     */
    @Test
    public void testGetSubscriptionExpired() {
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.getSubscription(100000004L);

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), is(
                allOf(
                        hasProperty("id", is(100000004L)),
                        hasProperty("startDate", is(Optional.of(LocalDate.parse("2011-01-01")))),
                        hasProperty("endDate", is(Optional.of(LocalDate.parse("2011-12-31")))),
                        hasProperty("terminated", is(false)),
                        hasProperty("suspended", is(false)),
                        hasProperty("subscriberId", is(100000004L)),
                        hasProperty("active", is(false)),
                        hasProperty("canBeSuspended", is(true)),
                        hasProperty("canBeTerminated", is(true)),
                        hasProperty("canBeUnsuspended", is(false))
                )
        ));
    }

    /**
     * Test getSubscription when the subscription exists and is active due to the subscription period starting on
     * the test date.
     */
    @Test
    public void testGetSubscriptionStarting() {
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.getSubscription(100000008L);

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), is(
                allOf(
                        hasProperty("id", is(100000008L)),
                        hasProperty("startDate", is(Optional.of(LocalDate.parse("2012-06-03")))),
                        hasProperty("endDate", is(Optional.of(LocalDate.parse("2012-12-31")))),
                        hasProperty("terminated", is(false)),
                        hasProperty("suspended", is(false)),
                        hasProperty("subscriberId", is(100000008L)),
                        hasProperty("active", is(true)),
                        hasProperty("canBeSuspended", is(true)),
                        hasProperty("canBeTerminated", is(true)),
                        hasProperty("canBeUnsuspended", is(false))
                )
        ));
    }

    /**
     * Test getSubscription when the subscription exists and is active due to the subscription period ending on
     * the test date.
     */
    @Test
    public void testGetSubscriptionEnding() {
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.getSubscription(100000009L);

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), is(
                allOf(
                        hasProperty("id", is(100000009L)),
                        hasProperty("startDate", is(Optional.of(LocalDate.parse("2012-01-01")))),
                        hasProperty("endDate", is(Optional.of(LocalDate.parse("2012-06-03")))),
                        hasProperty("terminated", is(false)),
                        hasProperty("suspended", is(false)),
                        hasProperty("subscriberId", is(100000008L)),
                        hasProperty("active", is(true)),
                        hasProperty("canBeSuspended", is(true)),
                        hasProperty("canBeTerminated", is(true)),
                        hasProperty("canBeUnsuspended", is(false))
                )
        ));
    }

    /**
     * Test getSubscription when the subscription exists and is active because
     * the start  date is empty and the end date is after the test date.
     */
    @Test
    public void testGetSubscriptionNoStartBeforeEnd() {
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.getSubscription(100000010L);

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), is(
                allOf(
                        hasProperty("id", is(100000010L)),
                        hasProperty("startDate", is(Optional.empty())),
                        hasProperty("endDate", is(Optional.of(LocalDate.parse("2012-12-31")))),
                        hasProperty("terminated", is(false)),
                        hasProperty("suspended", is(false)),
                        hasProperty("subscriberId", is(100000009L)),
                        hasProperty("active", is(true)),
                        hasProperty("canBeSuspended", is(true)),
                        hasProperty("canBeTerminated", is(true)),
                        hasProperty("canBeUnsuspended", is(false))
                )
        ));
    }

    /**
     * Test getSubscription when the subscription exists and
     * is inactive because the end date is before the test date.
     */
    @Test
    public void testGetSubscriptionNoStartAfterEnd() {
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.getSubscription(100000011L);

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), is(
                allOf(
                        hasProperty("id", is(100000011L)),
                        hasProperty("startDate", is(Optional.empty())),
                        hasProperty("endDate", is(Optional.of(LocalDate.parse("2012-06-02")))),
                        hasProperty("terminated", is(false)),
                        hasProperty("suspended", is(false)),
                        hasProperty("subscriberId", is(100000009L)),
                        hasProperty("active", is(false)),
                        hasProperty("canBeSuspended", is(true)),
                        hasProperty("canBeTerminated", is(true)),
                        hasProperty("canBeUnsuspended", is(false))
                )
        ));
    }

    /**
     * Test getSubscription when the subscription exists and is active because
     * the start date is before the test date and the end date is empty.
     */
    @Test
    public void testGetSubscriptionBeforeStartNoEnd() {
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.getSubscription(100000012L);

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), is(
                allOf(
                        hasProperty("id", is(100000012L)),
                        hasProperty("startDate", is(Optional.of(LocalDate.parse("2012-01-01")))),
                        hasProperty("endDate", is(Optional.empty())),
                        hasProperty("terminated", is(false)),
                        hasProperty("suspended", is(false)),
                        hasProperty("subscriberId", is(100000010L)),
                        hasProperty("active", is(true)),
                        hasProperty("canBeSuspended", is(true)),
                        hasProperty("canBeTerminated", is(true)),
                        hasProperty("canBeUnsuspended", is(false))
                )
        ));
    }

    /**
     * Test getSubscription when the subscription exists and is inactive because the start date is after the test date.
     */
    @Test
    public void testGetSubscriptionAfterStartNoEnd() {
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.getSubscription(100000013L);

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), is(
                allOf(
                        hasProperty("id", is(100000013L)),
                        hasProperty("startDate", is(Optional.of(LocalDate.parse("2012-06-04")))),
                        hasProperty("endDate", is(Optional.empty())),
                        hasProperty("terminated", is(false)),
                        hasProperty("suspended", is(false)),
                        hasProperty("subscriberId", is(100000010L)),
                        hasProperty("active", is(false)),
                        hasProperty("canBeSuspended", is(true)),
                        hasProperty("canBeTerminated", is(true)),
                        hasProperty("canBeUnsuspended", is(false))
                )
        ));
    }

    /**
     * Test deleteSubscription.
     */
    @Test
    public void testDeleteSubscription() {
        this.subscriptionService.deleteSubscription(100000010);
        this.subscriptionRepository.flush();

        Optional<Subscription> subscription = this.subscriptionRepository.findById(100000010L);
        Assert.assertTrue(subscription.isEmpty());
    }

    /**
     * Test deleteSubscription when the subscription does not exist. There should be no effect and no exception thrown.
     */
    @Test
    public void testDeleteSubscriptionNotFound() {
        this.subscriptionService.deleteSubscription(444L);
    }

    /**
     * Test createSubscription.
     */
    @Test
    public void testCreateSubscription() {
        NewSubscriptionCommandDto commandDto = new NewSubscriptionCommandDto(START_DATE, END_DATE,
                SUBSCRIPTION_CONTENT_ID.toString(), SUBSCRIBER_ID.toString());
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.createSubscription(commandDto);
        this.subscriptionRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertTrue(responseDto.get().getId() != null);
        Assert.assertEquals(responseDto.get().getStartDate(), START_DATE.map(LocalDate::parse));
        Assert.assertEquals(responseDto.get().getEndDate(), END_DATE.map(LocalDate::parse));
        assertThat(responseDto.get().getSubscriptionContent(), allOf(
                hasProperty("id", is(SUBSCRIPTION_CONTENT_ID)),
                hasProperty("subscriptionResource", allOf(
                        hasProperty("id", is(100000002L)),
                        hasProperty("resourceUri", is("https://universal-reference.com/music")),
                        hasProperty("resourceDescription", is("Universal Reference Music library"))
                ))
        ));
        Assert.assertEquals(responseDto.get().getSubscriberId(), SUBSCRIBER_ID);

        Optional<Subscription> subscription = this.subscriptionRepository.findById(responseDto.get().getId());
        Assert.assertTrue(subscription.isPresent());
        Assert.assertEquals(subscription.get().getStartDate(), START_DATE.map(LocalDate::parse));
        Assert.assertEquals(subscription.get().getEndDate(), END_DATE.map(LocalDate::parse));
        Assert.assertEquals(subscription.get().getSubscriptionContentId(), SUBSCRIPTION_CONTENT_ID);
        Assert.assertEquals(subscription.get().getSubscriberId(), SUBSCRIBER_ID);
    }

    /**
     * Test createSubscription with empty start and end dates.
     */
    @Test
    public void testCreateSubscriptionEmptyStartEnd() {
        NewSubscriptionCommandDto commandDto = new NewSubscriptionCommandDto(Optional.empty(), Optional.empty(),
                SUBSCRIPTION_CONTENT_ID.toString(), SUBSCRIBER_ID.toString());
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.createSubscription(commandDto);
        this.subscriptionRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertTrue(responseDto.get().getId() != null);
        Assert.assertEquals(responseDto.get().getStartDate(), Optional.empty());
        Assert.assertEquals(responseDto.get().getEndDate(), Optional.empty());
        Assert.assertEquals(responseDto.get().getSubscriberId(), SUBSCRIBER_ID);

        Optional<Subscription> subscription = this.subscriptionRepository.findById(responseDto.get().getId());
        Assert.assertTrue(subscription.isPresent());
        Assert.assertEquals(subscription.get().getStartDate(), Optional.empty());
        Assert.assertEquals(subscription.get().getEndDate(), Optional.empty());
        Assert.assertEquals(subscription.get().getSubscriptionContentId(), SUBSCRIPTION_CONTENT_ID);
        Assert.assertEquals(subscription.get().getSubscriberId(), SUBSCRIBER_ID);
    }

    /**
     * Test createSubscription when the dates are invalid because the start date occurs before the end date.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testCreateSubscriptionInvalidDates() {
        NewSubscriptionCommandDto commandDto = new NewSubscriptionCommandDto(END_DATE, START_DATE,
                SUBSCRIPTION_CONTENT_ID.toString(), SUBSCRIBER_ID.toString());
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.createSubscription(commandDto);
    }

    /**
     * Test updateSubscriptionDates when the dates are set to different dates.
     */
    @Test
    public void testUpdateSubscriptionDates() {
        SubscriptionDatesCommandDto commandDto = new SubscriptionDatesCommandDto(START_DATE, END_DATE);
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.updateSubscriptionDates(100000006L,
                commandDto);
        this.subscriptionRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertEquals(responseDto.get().getStartDate(), START_DATE.map(LocalDate::parse));
        Assert.assertEquals(responseDto.get().getEndDate(), END_DATE.map(LocalDate::parse));

        Optional<Subscription> subscription = this.subscriptionRepository.findById(100000006L);
        subscription.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscription.isPresent());
        Assert.assertEquals(subscription.get().getStartDate(), START_DATE.map(LocalDate::parse));
        Assert.assertEquals(subscription.get().getEndDate(), END_DATE.map(LocalDate::parse));
    }

    /**
     * Test updateSubscriptionDates when the dates are set to empty dates.
     */
    @Test
    public void testUpdateSubscriptionDatesEmpty() {
        SubscriptionDatesCommandDto commandDto = new SubscriptionDatesCommandDto(Optional.empty(), Optional.empty());
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.updateSubscriptionDates(100000006L,
                commandDto);
        this.subscriptionRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertEquals(responseDto.get().getStartDate(), Optional.empty());
        Assert.assertEquals(responseDto.get().getEndDate(), Optional.empty());

        Optional<Subscription> subscription = this.subscriptionRepository.findById(100000006L);
        subscription.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscription.isPresent());
        Assert.assertTrue(subscription.get().getStartDate().isEmpty());
        Assert.assertTrue(subscription.get().getEndDate().isEmpty());
    }

    /**
     * Test updateSubscriptionDates when the dates are invalid because the start date occurs before the end date.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testUpdateSubscriptionDatesInvalid() {
        SubscriptionDatesCommandDto commandDto = new SubscriptionDatesCommandDto(END_DATE, START_DATE);
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.updateSubscriptionDates(100000006L,
                commandDto);
    }

    /**
     * Test updateSubscriptionDates when the subscription does not exist.
     */
    @Test
    public void testUpdateSubscriptionDatesNotExists() {
        SubscriptionDatesCommandDto commandDto = new SubscriptionDatesCommandDto(END_DATE, START_DATE);
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.updateSubscriptionDates(123L,
                commandDto);

        Assert.assertTrue(responseDto.isEmpty());
    }

    /**
     * Test updateSubscriptionContentId.
     */
    @Test
    public void testUpdateSubscriptionContentId() {
        SubscriptionContentIdCommandDto commandDto = new SubscriptionContentIdCommandDto("100000004");
        Optional<SubscriptionResponseDto> responseDto
                = this.subscriptionService.updateSubscriptionContentId(100000006L, commandDto);
        this.subscriptionRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get().getSubscriptionContent(), allOf(
                hasProperty("id", is(100000004L)),
                hasProperty("subscriptionResource", allOf(
                        hasProperty("id", is(100000004L)),
                        hasProperty("resourceUri", is("urn:zodiac2000.co.uk:data")),
                        hasProperty("resourceDescription", is("Zodiac 2000 Data"))
                ))
        ));


        Optional<Subscription> subscription = this.subscriptionRepository.findById(100000006L);
        subscription.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscription.isPresent());
        Assert.assertEquals(subscription.get().getSubscriptionContentId(), 100000004L);
    }

    /**
     * Test updateSubscriptionContentId when the subscription does not exist.
     */
    @Test
    public void testUpdateSubscriptionContentIdNotExists() {
        SubscriptionContentIdCommandDto commandDto = new SubscriptionContentIdCommandDto("100000004");
        Optional<SubscriptionResponseDto> responseDto
                = this.subscriptionService.updateSubscriptionContentId(123L, commandDto);

        Assert.assertTrue(responseDto.isEmpty());
    }

    /**
     * Test suspendSubscription when the subscription exists and can be suspended.
     */
    @Test
    public void testSuspendSubscription() {
        final long subscriptionId = 100000001L;
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.suspendSubscription(subscriptionId);
        this.subscriptionRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertEquals(responseDto.get().getId(), subscriptionId);
        Assert.assertTrue(responseDto.get().isSuspended());
        Assert.assertFalse(responseDto.get().isTerminated());
        Assert.assertFalse(responseDto.get().isActive());

        Optional<Subscription> subscription = this.subscriptionRepository.findById(subscriptionId);
        subscription.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscription.isPresent());
        Assert.assertTrue(subscription.get().isSuspended());
        Assert.assertFalse(subscription.get().isTerminated());
        Assert.assertFalse(subscription.get().isActive(LocalDate.now(TestClockConfiguration.TEST_CLOCK)));
    }

    /**
     * Test suspendSubscription when the subscription exists but cannot be suspended because the subscription is
     * already suspended.
     */
    @Test
    public void testSuspendSubscriptionAlreadySuspended() {
        final long subscriptionId = 100000007L;
        Optional<SubscriptionResponseDto> responseDto = Optional.empty();
        try {
            responseDto = this.subscriptionService.suspendSubscription(subscriptionId);
            Assert.fail("IllegalStateException should have been thrown");
        } catch (IllegalStateException e) {
            Assert.assertEquals(e.getMessage(),
                    "This subscription cannot be suspended because it is already suspended");
        }

        // Response DTO should be empty because of the exception.
        Assert.assertTrue(responseDto.isEmpty());

        // Suspended state of the subscription is unchanged.
        Optional<Subscription> subscription = this.subscriptionRepository.findById(subscriptionId);
        subscription.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscription.isPresent());
        Assert.assertTrue(subscription.get().isSuspended());
    }

    /**
     * Test suspendSubscription when the subscription does not exist.
     */
    @Test
    public void testSuspendSubscriptionNotFound() {
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.suspendSubscription(3333L);

        Assert.assertTrue(responseDto.isEmpty());
    }

    /**
     * Test terminateSubscription when the subscription exists and can be terminated.
     */
    @Test
    public void testTerminateSubscription() {
        final long subscriptionId = 100000001L;
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.terminateSubscription(subscriptionId);
        this.subscriptionRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertEquals(responseDto.get().getId(), subscriptionId);
        Assert.assertTrue(responseDto.get().isSuspended());
        Assert.assertTrue(responseDto.get().isTerminated());
        Assert.assertFalse(responseDto.get().isActive());

        Optional<Subscription> subscription = this.subscriptionRepository.findById(subscriptionId);
        subscription.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscription.isPresent());
        Assert.assertTrue(subscription.get().isSuspended());
        Assert.assertTrue(subscription.get().isTerminated());
        Assert.assertFalse(subscription.get().isActive(LocalDate.now(TestClockConfiguration.TEST_CLOCK)));
    }


    /**
     * Test terminateSubscription when the subscription exists in a suspended state and can be terminated.
     */
    @Test
    public void testTerminateSubscriptionAlreadySuspended() {
        final long subscriptionId = 100000014L;
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.terminateSubscription(subscriptionId);
        this.subscriptionRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertEquals(responseDto.get().getId(), subscriptionId);
        Assert.assertTrue(responseDto.get().isSuspended());
        Assert.assertTrue(responseDto.get().isTerminated());
        Assert.assertFalse(responseDto.get().isActive());

        Optional<Subscription> subscription = this.subscriptionRepository.findById(subscriptionId);
        subscription.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscription.isPresent());
        Assert.assertTrue(subscription.get().isSuspended());
        Assert.assertTrue(subscription.get().isTerminated());
        Assert.assertFalse(subscription.get().isActive(LocalDate.now(TestClockConfiguration.TEST_CLOCK)));
    }

    /**
     * Test terminateSubscription when the subscription exists but cannot be suspended because the subscription is
     * already suspended.
     */
    @Test
    public void testTerminateSubscriptionAlreadyTerminated() {
        final long subscriptionId = 100000007L;
        Optional<SubscriptionResponseDto> responseDto = Optional.empty();
        try {
            responseDto = this.subscriptionService.terminateSubscription(subscriptionId);
            Assert.fail("IllegalStateException should have been thrown");
        } catch (IllegalStateException e) {
            Assert.assertEquals(e.getMessage(),
                    "This subscription cannot be terminated because it is already terminated");
        }

        // Response DTO should be empty because of the exception.
        Assert.assertTrue(responseDto.isEmpty());

        // Suspended state of the subscription is unchanged.
        Optional<Subscription> subscription = this.subscriptionRepository.findById(subscriptionId);
        subscription.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscription.isPresent());
        Assert.assertTrue(subscription.get().isSuspended());
        Assert.assertTrue(subscription.get().isTerminated());
    }

    /**
     * Test terminateSubscription when the subscription does not exist.
     */
    @Test
    public void testTerminateSubscriptionNotFound() {
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.terminateSubscription(3333L);

        Assert.assertTrue(responseDto.isEmpty());
    }

    /**
     * Test unsuspendSubscription when the subscription exists and can be unsuspended because it is suspended but
     * not terminated.
     */
    @Test
    public void testUnsuspendSubscription() {
        final long subscriptionId = 100000014L;
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.unsuspendSubscription(subscriptionId);
        this.subscriptionRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertEquals(responseDto.get().getId(), subscriptionId);
        Assert.assertFalse(responseDto.get().isSuspended());
        Assert.assertFalse(responseDto.get().isTerminated());
        Assert.assertTrue(responseDto.get().isActive());

        Optional<Subscription> subscription = this.subscriptionRepository.findById(subscriptionId);
        subscription.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscription.isPresent());
        Assert.assertFalse(subscription.get().isSuspended());
        Assert.assertFalse(subscription.get().isTerminated());
        Assert.assertTrue(subscription.get().isActive(LocalDate.now(TestClockConfiguration.TEST_CLOCK)));
    }

    /**
     * Test unsuspendSubscription when the subscription exists and is suspended, but cannot be unsuspended because
     * the subscription is terminated.
     */
    @Test
    public void testUnsuspendSubscriptionAlreadyTerminated() {
        final long subscriptionId = 100000007L;
        Optional<SubscriptionResponseDto> responseDto = Optional.empty();
        try {
            responseDto = this.subscriptionService.unsuspendSubscription(subscriptionId);
            Assert.fail("IllegalStateException should have been thrown");
        } catch (IllegalStateException e) {
            Assert.assertEquals(e.getMessage(),
                    "This subscription cannot be unsuspended because it is terminated");
        }

        // Response DTO should be empty because of the exception.
        Assert.assertTrue(responseDto.isEmpty());

        // Suspended state of the subscription is unchanged.
        Optional<Subscription> subscription = this.subscriptionRepository.findById(subscriptionId);
        subscription.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscription.isPresent());
        Assert.assertTrue(subscription.get().isSuspended());
        Assert.assertTrue(subscription.get().isTerminated());
    }

    /**
     * Test unsuspendSubscription when the subscription exists  but cannot be unsuspended because
     * the subscription is not suspended.
     */
    @Test
    public void testUnsuspendSubscriptionNotSuspended() {
        final long subscriptionId = 100000001L;
        Optional<SubscriptionResponseDto> responseDto = Optional.empty();
        try {
            responseDto = this.subscriptionService.unsuspendSubscription(subscriptionId);
            Assert.fail("IllegalStateException should have been thrown");
        } catch (IllegalStateException e) {
            Assert.assertEquals(e.getMessage(),
                    "This subscription cannot be unsuspended because it is not suspended");
        }

        // Response DTO should be empty because of the exception.
        Assert.assertTrue(responseDto.isEmpty());

        // Suspended state of the subscription is unchanged.
        Optional<Subscription> subscription = this.subscriptionRepository.findById(subscriptionId);
        subscription.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscription.isPresent());
        Assert.assertFalse(subscription.get().isSuspended());
        Assert.assertFalse(subscription.get().isTerminated());
    }

    /**
     * Test unsuspendSubscription when the subscription does not exist.
     */
    @Test
    public void testUnsuspendSubscriptionNotFound() {
        Optional<SubscriptionResponseDto> responseDto = this.subscriptionService.unsuspendSubscription(3333L);

        Assert.assertTrue(responseDto.isEmpty());
    }
}
