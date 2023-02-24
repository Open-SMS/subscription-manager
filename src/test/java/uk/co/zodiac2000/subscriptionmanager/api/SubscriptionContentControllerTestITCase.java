package uk.co.zodiac2000.subscriptionmanager.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.configuration.TestClockConfiguration;

/**
 * Integration tests for SubscriptionContentController.  These tests use MockMvc to test the controller without starting
 * a servlet environment.
 */
@SpringBootTest(classes = {TestClockConfiguration.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration.properties")
public class SubscriptionContentControllerTestITCase extends AbstractTransactionalTestNGSpringContextTests {

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
}
