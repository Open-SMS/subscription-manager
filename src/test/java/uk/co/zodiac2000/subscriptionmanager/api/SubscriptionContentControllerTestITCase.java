package uk.co.zodiac2000.subscriptionmanager.api;

import com.jayway.jsonpath.JsonPath;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.configuration.TestClockConfiguration;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent.SubscriptionContent;
import uk.co.zodiac2000.subscriptionmanager.repository.SubscriptionContentRepository;

/**
 * Integration tests for SubscriptionContentController.  These tests use MockMvc to test the controller without starting
 * a servlet environment.
 */
@SpringBootTest(classes = {TestClockConfiguration.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration.properties")
public class SubscriptionContentControllerTestITCase extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private SubscriptionContentRepository subscriptionContentRepository;

    @Autowired
    private MockMvc mockMvc;

    private WebTestClient client;

    @BeforeMethod
    public void setUpWebClient() {
        this.client = MockMvcWebTestClient.bindTo(this.mockMvc).build();
    }

    @BeforeMethod
    public void loadTestData() {
        executeSqlScript("classpath:test_data/subscription_resource_test_data.sql", false);
        executeSqlScript("classpath:test_data/subscription_content_test_data.sql", false);
    }

    /**
     * Test getSubscriptionContent.
     */
    @Test
    public void testGetSubscriptionContent() {
        this.client.get().uri("/subscription-content/100000003")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(100000003L)
                .jsonPath("$.contentDescription").isEqualTo("Universal Reference: Economics")
                .jsonPath("$.contentIdentifiers[0]").isEqualTo("ECONOMICS")
                .jsonPath("$.subscriptionResource.id").isEqualTo(100000003L)
                .jsonPath("$.subscriptionResource.resourceUri").isEqualTo("https://universal-reference.com/economics")
                .jsonPath("$.subscriptionResource.resourceDescription")
                    .isEqualTo("Universal Reference Economics library");
    }

    /**
     * Test getSubscriptionContent when the subscription content is not found.
     */
    @Test
    public void testGetSubscriptionContentNotFound() {
        this.client.get().uri("/subscription-content/1234")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Test createSubscriptionContent.
     */
    @Test
    public void testCreateSubscriptionContent() {
        String newSubscriptionContentJsonn = "{"
                + " \"contentDescription\":\"Test content\","
                + " \"subscriptionResourceId\":\"100000001\","
                + " \"contentIdentifiers\":["
                + "   {\"contentIdentifier\":\"TEST-CONTENT-ITEM-ONE\"},"
                + "   {\"contentIdentifier\":\"TEST-CONTENT-ITEM-TWO\"}"
                + " ]"
                + "}";
        EntityExchangeResult<byte[]> result = this.client.post().uri("/subscription-content")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newSubscriptionContentJsonn)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().returnResult();
        this.subscriptionContentRepository.flush();

        String responseBody = new String(result.getResponseBody());
        Integer id = JsonPath.read(responseBody, "$.id");
        Assert.assertNotNull(id);

        Optional<SubscriptionContent> subscriptionContent = this.subscriptionContentRepository.findById(id.longValue());
        Assert.assertTrue(subscriptionContent.isPresent());
        assertThat(subscriptionContent.get(), allOf(
                hasProperty("contentDescription", is("Test content")),
                hasProperty("subscriptionResourceId", is(100000001L)),
                hasProperty("contentIdentifiers", containsInAnyOrder(
                        hasProperty("contentIdentifier", is("TEST-CONTENT-ITEM-ONE")),
                        hasProperty("contentIdentifier", is("TEST-CONTENT-ITEM-TWO"))
                ))
        ));
    }

    /**
     * Test createSubscriptionContent when subscriptionResourceId is not an integer.
     */
    @Test
    public void testCreateSubscriptionContentSubscriptionResourceIdNotInteger() {
        String newSubscriptionContentJsonn = "{"
                + " \"contentDescription\":\"Test content\","
                + " \"subscriptionResourceId\":\"foo\","
                + " \"contentIdentifiers\":["
                + "   {\"contentIdentifier\":\"TEST-CONTENT-ITEM-ONE\"},"
                + "   {\"contentIdentifier\":\"TEST-CONTENT-ITEM-TWO\"}"
                + " ]"
                + "}";
        this.client.post().uri("/subscription-content")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newSubscriptionContentJsonn)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(res -> {
                    BindException ex = (BindException) ((MvcResult) res.getMockServerResult()).getResolvedException();
                    Assert.assertNotNull(ex);
                    Assert.assertEquals(ex.getErrorCount(), 1);
                    FieldError error = ex.getFieldError();
                    Assert.assertNotNull(error);
                    Assert.assertEquals(error.getField(), "subscriptionResourceId");
                    Assert.assertEquals(error.getDefaultMessage(),
                            "numeric value out of bounds (<18 digits>.<0 digits> expected)");
                });
    }

    /**
     * Test createSubscriptionContent when subscriptionResourceId does not reference a subscription resource
     * that exists in the system.
     */
    @Test
    public void testCreateSubscriptionContentSubscriptionResourceIdNotExists() {
        String newSubscriptionContentJsonn = "{"
                + " \"contentDescription\":\"Test content\","
                + " \"subscriptionResourceId\":\"42\","
                + " \"contentIdentifiers\":["
                + "   {\"contentIdentifier\":\"TEST-CONTENT-ITEM-ONE\"},"
                + "   {\"contentIdentifier\":\"TEST-CONTENT-ITEM-TWO\"}"
                + " ]"
                + "}";
        this.client.post().uri("/subscription-content")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newSubscriptionContentJsonn)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(res -> {
                    BindException ex = (BindException) ((MvcResult) res.getMockServerResult()).getResolvedException();
                    Assert.assertNotNull(ex);
                    Assert.assertEquals(ex.getErrorCount(), 1);
                    FieldError error = ex.getFieldError();
                    Assert.assertNotNull(error);
                    Assert.assertEquals(error.getField(), "subscriptionResourceId");
                    Assert.assertEquals(error.getDefaultMessage(),
                            "{uk.co.zodiac2000.subscriptionmanager.constraint.Exists}");
                });
    }
}
