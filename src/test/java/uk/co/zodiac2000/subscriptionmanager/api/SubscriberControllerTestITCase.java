package uk.co.zodiac2000.subscriptionmanager.api;

import com.jayway.jsonpath.JsonPath;
import java.util.Optional;
import java.util.Set;
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
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.OidcIdentifier;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.OidcIdentifierClaim;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.SamlIdentifier;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.Subscriber;
import uk.co.zodiac2000.subscriptionmanager.repository.SubscriberRepository;

/**
 * Integration tests for SubscriberController. These tests use MockMvc to test the controllers without starting
 * a servlet environment.
 */
@SpringBootTest(classes = {TestClockConfiguration.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration.properties")
public class SubscriberControllerTestITCase extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private SubscriberRepository subscriberRepository;

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
    }

    /**
     * Test getSubscriber when a subscriber is found.
     */
    @Test
    public void testGetSubscriber() {
        this.client.get().uri("/subscriber/100000005")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(100000005L)
                .jsonPath("$.subscriberName").isEqualTo("The Open University")
                .jsonPath("$.samlIdentifiers").isEmpty()
                .jsonPath("$.oidcIdentifiers[0].issuer").isEqualTo("https://oidc.open.ac.uk")
                .jsonPath("$.oidcIdentifiers[0].oidcIdentifierClaims[0].claimName").isEqualTo("sub")
                .jsonPath("$.oidcIdentifiers[0].oidcIdentifierClaims[0].claimValue").isEqualTo("343274");
    }

    /**
     * Test getSubscriber when a subscriber is not found.
     */
    @Test
    public void testGetSubscriberNotFound() {
        this.client.get().uri("/subscriber/444")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Test createSubscriber.
     */
    @Test
    public void testCreateSubscriber() {
        String newSubscriberJson = "{\"subscriberName\": \"Sheepcote University\"}";
        EntityExchangeResult<byte[]> result = this.client.post().uri("/subscriber")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newSubscriberJson)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().returnResult();

        String responseBody = new String(result.getResponseBody());
        Integer id = JsonPath.read(responseBody, "$.id");

        Assert.assertNotNull(id);

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(id.longValue());
        Assert.assertTrue(subscriber.isPresent());
        Assert.assertEquals(subscriber.get().getSubscriberName(), "Sheepcote University");
    }

    /**
     * Test createSubscriber when the subscriber name already exists in the system.
     */
    @Test
    public void testCreateSubscriberDuplicateName() {
        String newSubscriberJson = "{\"subscriberName\": \"The Open University\"}";
        this.client.post().uri("/subscriber")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newSubscriberJson)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(res -> {
                    BindException ex = (BindException) ((MvcResult) res.getMockServerResult()).getResolvedException();
                    Assert.assertNotNull(ex);
                    Assert.assertEquals(ex.getErrorCount(), 1);
                    FieldError error = ex.getFieldError();
                    Assert.assertNotNull(error);
                    Assert.assertEquals(error.getField(), "subscriberName");
                    Assert.assertEquals(error.getDefaultMessage(),
                            "{uk.co.zodiac2000.subscriptionmanager.constraint.DoesNotExist}");
                });
    }

    /**
     * Test deleteSubscriber.
     */
    @Test
    public void testDeleteSubscriber() {
        this.client.delete().uri("/subscriber/100000004")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(100000004L);
        Assert.assertTrue(subscriber.isEmpty());
    }

    /**
     * Test updateSubscriberName.
     */
    @Test
    public void testUpdateSubscriberName() {
        String updatedSubscriberNameJson = "{\"subscriberName\": \"Sheepcote University\"}";
        EntityExchangeResult<byte[]> result = this.client.put().uri("/subscriber/100000004/subscriber-name")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedSubscriberNameJson)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().returnResult();

        String responseBody = new String(result.getResponseBody());
        Integer id = JsonPath.read(responseBody, "$.id");
        Assert.assertEquals(id, 100000004);

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(100000004L);
        Assert.assertTrue(subscriber.isPresent());
        Assert.assertEquals(subscriber.get().getSubscriberName(), "Sheepcote University");
    }

    /**
     * Test updateSubscriberName when the subscriber name already exists in the system.
     */
    @Test
    public void testUpdateSubscriberNameDuplicate() {
        String updatedSubscriberNameJson = "{\"subscriberName\": \"The Open University\"}";
        this.client.put().uri("/subscriber/100000004/subscriber-name")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedSubscriberNameJson)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(res -> {
                    BindException ex = (BindException) ((MvcResult) res.getMockServerResult()).getResolvedException();
                    Assert.assertNotNull(ex);
                    Assert.assertEquals(ex.getErrorCount(), 1);
                    FieldError error = ex.getFieldError();
                    Assert.assertNotNull(error);
                    Assert.assertEquals(error.getField(), "subscriberName");
                    Assert.assertEquals(error.getDefaultMessage(),
                            "{uk.co.zodiac2000.subscriptionmanager.constraint.DoesNotExist}");
                });
    }

    /**
     * Test setSamlIdentifiers.
     */
    @Test
    public void testSetSamlIdentifiers() {
        String samlIdentifiers = "{"
                + " \"samlIdentifiers\":["
                + "  {"
                + "   \"entityId\": \"http://saml.example.com\","
                + "   \"scopedAffiliation\": \"student@example.com\""
                + "  },"
                + "  {"
                + "   \"entityId\": \"http://saml-server.example.com/ident\","
                + "   \"scopedAffiliation\": \"staff@example.com\""
                + "  }"
                + " ]"
                + "}";
        EntityExchangeResult<byte[]> result = this.client.put().uri("/subscriber/100000004/saml-identifiers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(samlIdentifiers)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().returnResult();

        String responseBody = new String(result.getResponseBody());
        Integer id = JsonPath.read(responseBody, "$.id");
        Assert.assertEquals(id, 100000004);

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(100000004L);
        Assert.assertTrue(subscriber.isPresent());
        assertThat(subscriber.get().getSamlIdentifiers(), containsInAnyOrder(
                equalTo(new SamlIdentifier("http://saml.example.com", "student@example.com")),
                equalTo(new SamlIdentifier("http://saml-server.example.com/ident", "staff@example.com"))
        ));
    }

    /**
     * Test setOidcIdentifiers.
     */
    @Test
    public void testSetOidcIdentifiers() {
        String oidcIdentifiers = "{"
                + " \"oidcIdentifiers\": ["
                + "  {"
                + "   \"issuer\":\"https://accounts.google.com\","
                + "   \"oidcIdentifierClaims\" : ["
                + "     {"
                + "      \"claimValue\":\"d2liYmxlCg==\","
                + "      \"claimName\" : \"sub\""
                + "     }"
                + "   ]"
                + "  }"
                + " ]"
                + "}";
        EntityExchangeResult<byte[]> result = this.client.put().uri("/subscriber/100000004/oidc-identifiers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(oidcIdentifiers)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().returnResult();

        String responseBody = new String(result.getResponseBody());
        Integer id = JsonPath.read(responseBody, "$.id");
        Assert.assertEquals(id, 100000004);

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(100000004L);
        Assert.assertTrue(subscriber.isPresent());
        assertThat(subscriber.get().getOidcIdentifiers(), contains(
                allOf(
                        hasProperty("issuer", is("https://accounts.google.com")),
                        hasProperty("oidcIdentifierClaims", contains(
                                allOf(
                                        hasProperty("claimName", is("sub")),
                                        hasProperty("claimValue", is("d2liYmxlCg=="))
                                )
                        ))
                )
        ));

    }
}
