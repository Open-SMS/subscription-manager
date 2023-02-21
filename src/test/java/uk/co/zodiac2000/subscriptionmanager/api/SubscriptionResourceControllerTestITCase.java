package uk.co.zodiac2000.subscriptionmanager.api;

import com.jayway.jsonpath.JsonPath;
import java.net.URI;
import java.util.Optional;
import javax.persistence.EntityManager;
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
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptionresource.SubscriptionResource;
import uk.co.zodiac2000.subscriptionmanager.repository.SubscriptionResourceRepository;

/**
 * Integration tests for SubscriptionResourceController. These tests use MockMvc to test the controller without starting
 * a servlet environment.
 */
@SpringBootTest(classes = {TestClockConfiguration.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration.properties")
public class SubscriptionResourceControllerTestITCase extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private SubscriptionResourceRepository subscriptionResourceRepository;

    @Autowired
    private EntityManager entityManager;

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
    }

    /**
     * Test getSubscriptionResource.
     */
    @Test
    public void testGetSubscriptionResource() {
        this.client.get().uri("/subscription-resource/100000003")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(100000003L)
                .jsonPath("$.resourceDescription").isEqualTo("Universal Reference Economics library")
                .jsonPath("$.resourceUri").isEqualTo("https://universal-reference.com/economics");

    }

    /**
     * Test createSubscriptionResource.
     */
    @Test
    public void testCreateSubscriptionResource() {
        String newSubscriptionResourceJson = "{"
                + "\"resourceUri\":\"https://www.camford-research.uk\","
                + "\"resourceDescription\":\"Camford Research\""
                + "}";
        EntityExchangeResult<byte[]> result = this.client.post().uri("/subscription-resource")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newSubscriptionResourceJson)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().returnResult();
        this.subscriptionResourceRepository.flush();

        String responseBody = new String(result.getResponseBody());
        Integer id = JsonPath.read(responseBody, "$.id");

        Assert.assertNotNull(id);

        Optional<SubscriptionResource> subscriptionResource
                = this.subscriptionResourceRepository.findById(id.longValue());
        subscriptionResource.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscriptionResource.isPresent());
        Assert.assertEquals(subscriptionResource.get().getResourceUri(), URI.create("https://www.camford-research.uk"));
        Assert.assertEquals(subscriptionResource.get().getResourceDescription(), "Camford Research");
    }

    /**
     * Test createSubscriptionResource when a subscription resource already exists with this resource URI.
     */
    @Test
    public void testCreateSubscriptionResourceDuplicateUri() {
        String newSubscriptionResourceJson = "{"
                + "\"resourceUri\":\"urn:zodiac2000.co.uk:data\","
                + "\"resourceDescription\":\"Zodiac 2000 Data\""
                + "}";
        this.client.post().uri("/subscription-resource")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newSubscriptionResourceJson)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(res -> {
                    BindException ex = (BindException) ((MvcResult) res.getMockServerResult()).getResolvedException();
                    Assert.assertNotNull(ex);
                    Assert.assertEquals(ex.getErrorCount(), 1);
                    FieldError error = ex.getFieldError();
                    Assert.assertNotNull(error);
                    Assert.assertEquals(error.getField(), "resourceUri");
                    Assert.assertEquals(error.getDefaultMessage(),
                            "{uk.co.zodiac2000.subscriptionmanager.constraint.DoesNotExist}");
                });
    }

    /**
     * Test createSubscriptionResource when a the resourceUri is not a valid absolute URI.
     */
    @Test
    public void testCreateSubscriptionResourceInvalidUri() {
        String newSubscriptionResourceJson = "{"
                + "\"resourceUri\":\"urnzodiac2000.co.uk#data\","
                + "\"resourceDescription\":\"Zodiac 2000 Data\""
                + "}";
        this.client.post().uri("/subscription-resource")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newSubscriptionResourceJson)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(res -> {
                    BindException ex = (BindException) ((MvcResult) res.getMockServerResult()).getResolvedException();
                    Assert.assertNotNull(ex);
                    Assert.assertEquals(ex.getErrorCount(), 1);
                    FieldError error = ex.getFieldError();
                    Assert.assertNotNull(error);
                    Assert.assertEquals(error.getField(), "resourceUri");
                    Assert.assertEquals(error.getDefaultMessage(),
                            "{uk.co.zodiac2000.subscriptionmanager.constraint.ValidUriString}");
                });
    }
}
