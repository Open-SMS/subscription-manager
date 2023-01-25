package uk.co.zodiac2000.subscriptionmanager.api;

import com.jayway.jsonpath.JsonPath;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.configuration.TestClockConfiguration;
import uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription;
import uk.co.zodiac2000.subscriptionmanager.repository.SubscriptionRepository;

/**
 * Integration tests for SubscriptionController. These tests use MockMvc to test the controller without starting
 * a servlet environment.
 */
@SpringBootTest(classes = {TestClockConfiguration.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration.properties")
public class SubscriptionControllerTestITCase extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private MockMvc mockMvc;

    private WebTestClient client;

    @BeforeMethod
    public void setUpWebClient() {
        this.client = MockMvcWebTestClient.bindTo(this.mockMvc).build();
    }

    @BeforeMethod
    public void loadTestData() {
        executeSqlScript("classpath:test_data/claim_name_test_data.sql", false);
        executeSqlScript("classpath:test_data/subscriber_test_data.sql", false);
        executeSqlScript("classpath:test_data/subscription_test_data.sql", false);
    }

    /**
     * Test getSubscription.
     */
    @Test
    public void testGetSubscription() {
        this.client.get().uri("/subscription/100000013")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(100000013L)
                .jsonPath("$.startDate").isEqualTo("2012-06-04")
                .jsonPath("$.endDate").isEmpty()
                .jsonPath("$.terminated").isEqualTo(false)
                .jsonPath("$.suspended").isEqualTo(false)
                .jsonPath("$.contentIdentifier").isEqualTo("CONTENT-2")
                .jsonPath("$.subscriberId").isEqualTo(100000010L)
                .jsonPath("$.active").isEqualTo(false)
                .jsonPath("$.canBeSuspended").isEqualTo(true)
                .jsonPath("$.canBeTerminated").isEqualTo(true)
                .jsonPath("$.canBeUnsuspended").isEqualTo(false);
    }

    /**
     * Test createSubscription.
     */
    @Test
    public void testCreateSubscription() {
        String newSubscriptionJson = "{"
                + "\"startDate\":\"2012-01-01\","
                + "\"endDate\":\"2020-01-01\","
                + "\"contentIdentifier\":\"CONTENT-X\","
                + "\"subscriberId\":100000010"
                + "}";
        EntityExchangeResult<byte[]> result = this.client.post().uri("/subscription")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newSubscriptionJson)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().returnResult();

        String responseBody = new String(result.getResponseBody());
        Integer id = JsonPath.read(responseBody, "$.id");

        Assert.assertNotNull(id);
        
        Optional<Subscription> subscription = this.subscriptionRepository.findById(id.longValue());
        Assert.assertTrue(subscription.isPresent());
        Assert.assertTrue(subscription.get().getStartDate().isPresent());
        Assert.assertEquals(subscription.get().getStartDate().get(), LocalDate.parse("2012-01-01"));
        Assert.assertTrue(subscription.get().getEndDate().isPresent());
        Assert.assertEquals(subscription.get().getEndDate().get(), LocalDate.parse("2020-01-01"));
        Assert.assertEquals(subscription.get().getContentIdentifier(), "CONTENT-X");
        Assert.assertEquals(subscription.get().getSubscriberId(), 100000010L);
    }

    /**
     * Test deleteSubscription.
     */
    @Test
    public void testDeleteSubscription() {
        this.client.delete().uri("/subscription/100000004")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();

        Optional<Subscription> subscription = this.subscriptionRepository.findById(100000004L);
        Assert.assertTrue(subscription.isEmpty());
    }

    /**
     * Test updateSubscriptionDates.
     */
    @Test
    public void testUpdateSubscriptionDates() {
        String updatedSubscriptionDatesJson = "{"
                + "\"startDate\":\"2014-01-01\","
                + "\"endDate\":\"2019-01-01\""
                + "}";
        EntityExchangeResult<byte[]> result = this.client.put().uri("/subscription/100000004/dates")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedSubscriptionDatesJson)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().returnResult();

        String responseBody = new String(result.getResponseBody());
        Integer id = JsonPath.read(responseBody, "$.id");
        Assert.assertEquals(id, 100000004);

        Optional<Subscription> subscription = this.subscriptionRepository.findById(100000004L);
        Assert.assertTrue(subscription.isPresent());
        Assert.assertTrue(subscription.get().getStartDate().isPresent());
        Assert.assertEquals(subscription.get().getStartDate().get(), LocalDate.parse("2014-01-01"));
        Assert.assertTrue(subscription.get().getEndDate().isPresent());
        Assert.assertEquals(subscription.get().getEndDate().get(), LocalDate.parse("2019-01-01"));
    }

    /**
     * Test updateSubscriptionContentIdentifier.
     */
    @Test
    public void testUpdateSubscriptionContentIdentifier() {
        String updatedSubscriptionDatesJson = "{"
                + "\"contentIdentifier\":\"CONTENT-Z\""
                + "}";
        EntityExchangeResult<byte[]> result = this.client.put().uri("/subscription/100000004/content-identifier")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedSubscriptionDatesJson)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().returnResult();

        String responseBody = new String(result.getResponseBody());
        Integer id = JsonPath.read(responseBody, "$.id");
        Assert.assertEquals(id, 100000004);

        Optional<Subscription> subscription = this.subscriptionRepository.findById(100000004L);
        Assert.assertTrue(subscription.isPresent());
        Assert.assertEquals(subscription.get().getContentIdentifier(), "CONTENT-Z");
    }

    /**
     * Test suspendSubscription when the subscription can be suspended.
     */
    @Test
    public void testSuspendSubscription() {
        EntityExchangeResult<byte[]> result = this.client.put().uri("/subscription/100000004/suspend")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().returnResult();

        String responseBody = new String(result.getResponseBody());
        Integer id = JsonPath.read(responseBody, "$.id");
        Assert.assertEquals(id, 100000004);

        Optional<Subscription> subscription = this.subscriptionRepository.findById(100000004L);
        Assert.assertTrue(subscription.isPresent());
        Assert.assertTrue(subscription.get().isSuspended());
    }

    /**
     * Test suspendSubscription when the subscription cannot be suspended.
     */
    @Test
    public void testSuspendSubscriptionAlreadySuspended() {
        this.client.put().uri("/subscription/100000014/suspend")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(res -> {
                    IllegalStateException ex
                            = (IllegalStateException) ((MvcResult) res.getMockServerResult()).getResolvedException();
                    Assert.assertEquals(ex.getMessage(),
                            "This subscription cannot be suspended because it is already suspended");
                });
    }

    /**
     * Test terminateSubscription.
     */
    @Test
    public void testTerminateSubscription() {
        EntityExchangeResult<byte[]> result = this.client.put().uri("/subscription/100000004/terminate")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().returnResult();

        String responseBody = new String(result.getResponseBody());
        Integer id = JsonPath.read(responseBody, "$.id");
        Assert.assertEquals(id, 100000004);

        Optional<Subscription> subscription = this.subscriptionRepository.findById(100000004L);
        Assert.assertTrue(subscription.isPresent());
        Assert.assertTrue(subscription.get().isSuspended());
        Assert.assertTrue(subscription.get().isTerminated());
    }

    /**
     * Test unsuspendSubscription.
     */
    @Test
    public void testUnsuspendSubscription() {
        EntityExchangeResult<byte[]> result = this.client.put().uri("/subscription/100000014/unsuspend")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().returnResult();

        String responseBody = new String(result.getResponseBody());
        Integer id = JsonPath.read(responseBody, "$.id");
        Assert.assertEquals(id, 100000014);

        Optional<Subscription> subscription = this.subscriptionRepository.findById(100000014L);
        Assert.assertTrue(subscription.isPresent());
        Assert.assertFalse(subscription.get().isSuspended());
    }
}
