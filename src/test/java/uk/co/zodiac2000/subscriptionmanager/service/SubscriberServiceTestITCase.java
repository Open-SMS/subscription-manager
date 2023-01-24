package uk.co.zodiac2000.subscriptionmanager.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.Subscriber;
import uk.co.zodiac2000.subscriptionmanager.repository.SubscriberRepository;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.NewSubscriberCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierClaimCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SamlIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SamlIdentifierRequestDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SubscriberNameCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SubscriberResponseDto;

/**
 * Integration tests for SubscriberService.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration.properties")
public class SubscriberServiceTestITCase extends AbstractTransactionalTestNGSpringContextTests {

    private static final String SUBSCRIBER_NAME = "Trevor Horn";
    private static final String UPDATED_SUBSCRIBER_NAME = "Ben Stokes";

    private static final NewSubscriberCommandDto NEW_SUBSCRIBER_COMMAND_DTO
            = new NewSubscriberCommandDto(SUBSCRIBER_NAME);

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeMethod
    public void loadTestData() {
        executeSqlScript("classpath:test_data/claim_name_test_data.sql", false);
        executeSqlScript("classpath:test_data/subscriber_test_data.sql", false);
    }

    /**
     * Test getSubscriberById when the subscriber exists.
     */
    @Test
    public void testGetSubscriberById() {
        Optional<SubscriberResponseDto> subscriber = this.subscriberService.getSubscriberById(100000003L);

        Assert.assertTrue(subscriber.isPresent());
        Assert.assertEquals(subscriber.get().getId(), 100000003L);
        Assert.assertEquals(subscriber.get().getSubscriberName(), "Barbara Castle");
        Assert.assertTrue(subscriber.get().getSamlIdentifiers().isEmpty());
        Assert.assertTrue(subscriber.get().getOidcIdentifiers().isEmpty());
    }

    /**
     * Test getSubscriberById when the subscriber does not exist.
     */
    @Test
    public void testGetSubscriberByIdNotFound() {
        Optional<SubscriberResponseDto> subscriber = this.subscriberService.getSubscriberById(42L);

        Assert.assertTrue(subscriber.isEmpty());
    }

    /**
     * Test getSubscriberById when the subscriber exists and is associated with SAML identifiers.
     */
    @Test
    public void testGetSubscriberByIdSamlIdentifiers() {
        Optional<SubscriberResponseDto> subscriber = this.subscriberService.getSubscriberById(100000004L);

        Assert.assertTrue(subscriber.isPresent());
        Assert.assertEquals(subscriber.get().getId(), 100000004L);
        Assert.assertEquals(subscriber.get().getSubscriberName(), "Cardiff University");
        assertThat(subscriber.get().getSamlIdentifiers(), contains(
                allOf(
                        hasProperty("entityId", is("https://idp.cardiff.ac.uk/shibboleth")),
                        hasProperty("scopedAffiliation", is("member@idp.cardiff.ac.uk"))
                ),
                allOf(
                        hasProperty("entityId", is("https://idp.cardiff.ac.uk/shibboleth")),
                        hasProperty("scopedAffiliation", is("staff@idp.cardiff.ac.uk"))
                )
        ));
        Assert.assertTrue(subscriber.get().getOidcIdentifiers().isEmpty());
    }

    /**
     * Test getSubscriberById when the subscriber exists and is associated with OIDC identifiers.
     */
    @Test
    public void testGetSubscriberByIdOidcIdentifiers() {
        Optional<SubscriberResponseDto> subscriber = this.subscriberService.getSubscriberById(100000001L);

        Assert.assertTrue(subscriber.isPresent());
        Assert.assertEquals(subscriber.get().getId(), 100000001L);
        Assert.assertEquals(subscriber.get().getSubscriberName(), "Daphne Oram");
        Assert.assertTrue(subscriber.get().getSamlIdentifiers().isEmpty());
        assertThat(subscriber.get().getOidcIdentifiers(), contains(
                allOf(
                        hasProperty("issuer", is("https://www.facebook.com")),
                        hasProperty("oidcIdentifierClaims", contains(
                                allOf(
                                        hasProperty("claimName", is("sub")),
                                        hasProperty("claimValue", is("23987283"))
                                )
                        ))
                )
        ));
    }

    /**
     * Test createSubscriber.
     */
    @Test
    public void testCreateSubscriber() {
        Optional<SubscriberResponseDto> responseDto
                = this.subscriberService.createSubscriber(NEW_SUBSCRIBER_COMMAND_DTO);
        this.subscriberRepository.flush();

        Assert.assertTrue(responseDto.get().getId() > 0);
        Assert.assertEquals(responseDto.get().getSubscriberName(), SUBSCRIBER_NAME);

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(responseDto.get().getId());
        Assert.assertTrue(subscriber.isPresent());
        Assert.assertEquals(subscriber.get().getSubscriberName(), SUBSCRIBER_NAME);
        Assert.assertTrue(subscriber.get().getSamlIdentifiers().isEmpty());
        Assert.assertTrue(subscriber.get().getOidcIdentifiers().isEmpty());
    }

    /**
     * Test updateSubscriberName when the subscriber exists.
     */
    @Test
    public void testUpdateSubscriberName() {
        Optional<SubscriberResponseDto> responseDto = this.subscriberService.updateSubscriberName(
                new SubscriberNameCommandDto(100000003L, UPDATED_SUBSCRIBER_NAME));
        this.subscriberRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertEquals(responseDto.get().getSubscriberName(), UPDATED_SUBSCRIBER_NAME);

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(responseDto.get().getId());
        subscriber.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscriber.isPresent());
        Assert.assertEquals(subscriber.get().getSubscriberName(), UPDATED_SUBSCRIBER_NAME);
    }

    /**
     * Test updateSubscriberName when the subscriber does not exist.
     */
    @Test
    public void testUpdateSubscriberNameNotFound() {
        Optional<SubscriberResponseDto> responseDto = this.subscriberService.updateSubscriberName(
                new SubscriberNameCommandDto(42L, UPDATED_SUBSCRIBER_NAME));

        Assert.assertTrue(responseDto.isEmpty());
    }

    /**
     * Test deleteSubscriber. Note, this subscriber is not associated with any subscriptions. If it were then this
     * operation would fail due to the foreign key in the database.
     */
    @Test
    public void testDeleteSubscriber() {
        this.subscriberService.deleteSubscriber(100000003L);
        this.subscriberRepository.flush();

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(100000003L);
        Assert.assertTrue(subscriber.isEmpty());
    }

    /**
     * Test deleteSubscriber when the subscriber is associated with OidcIdentifier and SamlIdentifier objects.
     */
    @Test
    public void testDeleteSubscriberIdentifiers() {
        this.subscriberService.deleteSubscriber(100000011L);
        this.subscriberRepository.flush();

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(100000011L);
        Assert.assertTrue(subscriber.isEmpty());
    }

    /**
     * Test deleteSubscriber when the identified subscriber does not exist. No exception should be thrown in this
     * situation.
     */
    @Test
    public void testDeleteSubscriberNotFound() {
        this.subscriberService.deleteSubscriber(4321L);
    }

    /**
     * Test setSamlIdentifiers when the subscriber is already associated with some SAML identifiers but not OIDC
     * identifiers. The existing SAML identifier collection should be replaced.
     */
    @Test
    public void testSetSamlIdentifiersReplace() {
        Set<SamlIdentifierCommandDto> samlIdentifiers = Set.of(
                new SamlIdentifierCommandDto("http://example.com/shib", "member@example.com")
        );
        Optional<SubscriberResponseDto> responseDto
                = this.subscriberService.setSamlIdentifiers(100000004L, samlIdentifiers);
        this.subscriberRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertEquals(responseDto.get().getId(), 100000004L);
        assertThat(responseDto.get().getSamlIdentifiers(), contains(
                allOf(
                        hasProperty("entityId", is("http://example.com/shib")),
                        hasProperty("scopedAffiliation", is("member@example.com"))
                )
        ));
        Assert.assertTrue(responseDto.get().getOidcIdentifiers().isEmpty());

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(100000004L);
        subscriber.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscriber.isPresent());
        assertThat(subscriber.get().getSamlIdentifiers(), contains(
                allOf(
                        hasProperty("entityId", is("http://example.com/shib")),
                        hasProperty("scopedAffiliation", is("member@example.com"))
                )
        ));
        Assert.assertTrue(subscriber.get().getOidcIdentifiers().isEmpty());
    }

    /**
     * Test setSamlIdentifiers when the subscriber is not already associated with some SAML identifiers but is
     * associated with OIDC identifiers. The SAML identifiers should be created and the OIDC identifiers unmodified.
     */
    @Test
    public void testSetSamlIdentifiers() {
        Set<SamlIdentifierCommandDto> samlIdentifiers = Set.of(
                new SamlIdentifierCommandDto("http://open.ac.uk/shib", "staff@open.ac.uk"),
                new SamlIdentifierCommandDto("http://open.ac.uk/shib", "student@open.ac.uk")
        );
        Optional<SubscriberResponseDto> responseDto
                = this.subscriberService.setSamlIdentifiers(100000005L, samlIdentifiers);
        this.subscriberRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertEquals(responseDto.get().getId(), 100000005L);
        assertThat(responseDto.get().getSamlIdentifiers(), contains(
                allOf(
                        hasProperty("entityId", is("http://open.ac.uk/shib")),
                        hasProperty("scopedAffiliation", is("staff@open.ac.uk"))
                ),
                allOf(
                        hasProperty("entityId", is("http://open.ac.uk/shib")),
                        hasProperty("scopedAffiliation", is("student@open.ac.uk"))
                )
        ));
        assertThat(responseDto.get().getOidcIdentifiers(), contains(
                allOf(
                        hasProperty("issuer", is("https://oidc.open.ac.uk")),
                        hasProperty("oidcIdentifierClaims", contains(
                                allOf(
                                        hasProperty("claimName", is("sub")),
                                        hasProperty("claimValue", is("343274"))
                                )
                        ))
                )
        ));

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(100000005L);
        subscriber.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscriber.isPresent());
        Assert.assertEquals(subscriber.get().getSubscriberName(), "The Open University");
        assertThat(subscriber.get().getSamlIdentifiers(), contains(
                allOf(
                        hasProperty("entityId", is("http://open.ac.uk/shib")),
                        hasProperty("scopedAffiliation", is("staff@open.ac.uk"))
                ),
                allOf(
                        hasProperty("entityId", is("http://open.ac.uk/shib")),
                        hasProperty("scopedAffiliation", is("student@open.ac.uk"))
                )
        ));
        assertThat(subscriber.get().getOidcIdentifiers(), contains(
                allOf(
                        hasProperty("issuer", is("https://oidc.open.ac.uk")),
                        hasProperty("oidcIdentifierClaims", contains(
                                allOf(
                                        hasProperty("claimName", is("sub")),
                                        hasProperty("claimValue", is("343274"))
                                )
                        ))
                )
        ));
    }

    /**
     * Test setSamlIdentifiers when the subscriber doesn't exist.
     */
    @Test
    public void testSetSamlIdentifiersNotFound() {
        Set<SamlIdentifierCommandDto> samlIdentifiers = Set.of(
                new SamlIdentifierCommandDto("http://open.ac.uk/shib", "student@open.ac.uk")
        );
        Optional<SubscriberResponseDto> responseDto = this.subscriberService.setSamlIdentifiers(42L, samlIdentifiers);

        Assert.assertTrue(responseDto.isEmpty());
    }

    /**
     * Test setOidcIdentifiers when the subscriber is not associated with SAML or OIDC identifiers. There are two
     * OIDC identifiers, and the OIDC identifier with issuer https://auth.open.ac.uk is associated with two claims,
     * one of which includes a claim name that doesn't already exist in the system.
     */
    @Test
    public void testSetOidcIdentifiers() {
        Set<OidcIdentifierCommandDto> oidcIdentifiers = Set.of(
                new OidcIdentifierCommandDto("https://auth.open.ac.uk", List.of(
                        new OidcIdentifierClaimCommandDto("sub", "347839447"),
                        new OidcIdentifierClaimCommandDto("eduPersonScopedAffiliation", "staff@open.ac.uk")
                )),
                new OidcIdentifierCommandDto("https://accounts.google.com", List.of(
                        new OidcIdentifierClaimCommandDto("sub", "3DA52E3")
                ))
        );
        Optional<SubscriberResponseDto> responseDto
                = this.subscriberService.setOidcIdentifiers(100000002L, oidcIdentifiers);
        this.subscriberRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertEquals(responseDto.get().getId(), 100000002L);
        Assert.assertTrue(responseDto.get().getSamlIdentifiers().isEmpty());
        assertThat(responseDto.get().getOidcIdentifiers(), contains(
                allOf(
                        hasProperty("issuer", is("https://accounts.google.com")),
                        hasProperty("oidcIdentifierClaims", contains(
                                allOf(
                                        hasProperty("claimName", is("sub")),
                                        hasProperty("claimValue", is("3DA52E3"))
                                )
                        ))
                ),
                allOf(
                        hasProperty("issuer", is("https://auth.open.ac.uk")),
                        hasProperty("oidcIdentifierClaims", contains(
                                allOf(
                                        hasProperty("claimName", is("eduPersonScopedAffiliation")),
                                        hasProperty("claimValue", is("staff@open.ac.uk"))
                                ),
                                allOf(
                                        hasProperty("claimName", is("sub")),
                                        hasProperty("claimValue", is("347839447"))
                                )
                        ))
                )
        ));

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(100000002L);
        subscriber.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscriber.isPresent());
        Assert.assertTrue(subscriber.get().getSamlIdentifiers().isEmpty());
        assertThat(subscriber.get().getOidcIdentifiers(), containsInAnyOrder(
                allOf(
                        hasProperty("issuer", is("https://accounts.google.com")),
                        hasProperty("oidcIdentifierClaims", contains(
                                allOf(
                                        hasProperty("claimName", is("sub")),
                                        hasProperty("claimValue", is("3DA52E3"))
                                )
                        ))
                ),
                allOf(
                        hasProperty("issuer", is("https://auth.open.ac.uk")),
                        hasProperty("oidcIdentifierClaims", containsInAnyOrder(
                                allOf(
                                        hasProperty("claimName", is("eduPersonScopedAffiliation")),
                                        hasProperty("claimValue", is("staff@open.ac.uk"))
                                ),
                                allOf(
                                        hasProperty("claimName", is("sub")),
                                        hasProperty("claimValue", is("347839447"))
                                )

                        ))
                )
        ));
    }

    /**
     * Test setOidcIdentifiers when the subscriber is not associated with SAML or OIDC identifiers. There are two
     * OIDC identifiers both with issuer https://accounts.google.com but with different claims. This test
     * verifies both OIDC identifiers are persisted due to not being equal.
     */
    @Test
    public void testSetOidcIdentifiersSameIssuer() {
        Set<OidcIdentifierCommandDto> oidcIdentifiers = Set.of(
                new OidcIdentifierCommandDto("https://accounts.google.com", List.of(
                        new OidcIdentifierClaimCommandDto("sub", "3DA52E3")
                )),
                new OidcIdentifierCommandDto("https://accounts.google.com", List.of(
                        new OidcIdentifierClaimCommandDto("sub", "FA14276")
                ))
        );
        Optional<SubscriberResponseDto> responseDto
                = this.subscriberService.setOidcIdentifiers(100000002L, oidcIdentifiers);
        this.subscriberRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertEquals(responseDto.get().getId(), 100000002L);
        Assert.assertTrue(responseDto.get().getSamlIdentifiers().isEmpty());
        // Note, OidcIdentifierResponseDto objects are sorted by issuer. When issuer is the same the order in which
        // they're returned is indeterminite.
        assertThat(responseDto.get().getOidcIdentifiers(), containsInAnyOrder(
                allOf(
                        hasProperty("issuer", is("https://accounts.google.com")),
                        hasProperty("oidcIdentifierClaims", contains(
                                allOf(
                                        hasProperty("claimName", is("sub")),
                                        hasProperty("claimValue", is("3DA52E3"))
                                )
                        ))
                ),
                allOf(
                        hasProperty("issuer", is("https://accounts.google.com")),
                        hasProperty("oidcIdentifierClaims", contains(
                                allOf(
                                        hasProperty("claimName", is("sub")),
                                        hasProperty("claimValue", is("FA14276"))
                                )
                        ))
                )
        ));

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(100000002L);
        subscriber.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscriber.isPresent());
        Assert.assertTrue(subscriber.get().getSamlIdentifiers().isEmpty());
        assertThat(subscriber.get().getOidcIdentifiers(), containsInAnyOrder(
                allOf(
                        hasProperty("issuer", is("https://accounts.google.com")),
                        hasProperty("oidcIdentifierClaims", contains(
                                allOf(
                                        hasProperty("claimName", is("sub")),
                                        hasProperty("claimValue", is("3DA52E3"))
                                )
                        ))
                ),
                allOf(
                        hasProperty("issuer", is("https://accounts.google.com")),
                        hasProperty("oidcIdentifierClaims", contains(
                                allOf(
                                        hasProperty("claimName", is("sub")),
                                        hasProperty("claimValue", is("FA14276"))
                                )
                        ))
                )
        ));
    }

    /**
     * Test setOidcIdentifiers when the subscriber is not associated with SAML identifiers but is associated with OIDC
     * identifiers. The OIDC identifiers should be replaced.
     */
    @Test
    public void testSetOidcIdentifiersReplace() {
        Set<OidcIdentifierCommandDto> oidcIdentifiers = Set.of(
                new OidcIdentifierCommandDto("https://accounts.google.com", List.of(
                        new OidcIdentifierClaimCommandDto("sub", "3DA52E3")
                ))
        );
        Optional<SubscriberResponseDto> responseDto
                = this.subscriberService.setOidcIdentifiers(100000001L, oidcIdentifiers);
        this.subscriberRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertEquals(responseDto.get().getId(), 100000001L);
        Assert.assertTrue(responseDto.get().getSamlIdentifiers().isEmpty());
        assertThat(responseDto.get().getOidcIdentifiers(), contains(
                allOf(
                        hasProperty("issuer", is("https://accounts.google.com")),
                        hasProperty("oidcIdentifierClaims", contains(
                                allOf(
                                        hasProperty("claimName", is("sub")),
                                        hasProperty("claimValue", is("3DA52E3"))
                                )
                        ))
                )
        ));

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(100000001L);
        subscriber.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscriber.isPresent());
        Assert.assertTrue(subscriber.get().getSamlIdentifiers().isEmpty());
        assertThat(subscriber.get().getOidcIdentifiers(), contains(
                allOf(
                        hasProperty("issuer", is("https://accounts.google.com")),
                        hasProperty("oidcIdentifierClaims", contains(
                                allOf(
                                        hasProperty("claimName", is("sub")),
                                        hasProperty("claimValue", is("3DA52E3"))
                                )
                        ))
                )
        ));
    }

    /**
     * Test setOidcIdentifiers when the subscriber is not associated with SAML or OIDC identifiers. There is one
     * OIDC identifier that has no associated claims. This would be used when the subscriber should be identified
     * in an authorization request for any successful authentication with the specified issuer.
     */
    @Test
    public void testSetOidcIdentifiersNoClaims() {
        Set<OidcIdentifierCommandDto> oidcIdentifiers = Set.of(
                new OidcIdentifierCommandDto("https://auth.open.ac.uk", List.of())
        );
        Optional<SubscriberResponseDto> responseDto
                = this.subscriberService.setOidcIdentifiers(100000002L, oidcIdentifiers);
        this.subscriberRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertEquals(responseDto.get().getId(), 100000002L);
        Assert.assertTrue(responseDto.get().getSamlIdentifiers().isEmpty());
        assertThat(responseDto.get().getOidcIdentifiers(), contains(
                allOf(
                        hasProperty("issuer", is("https://auth.open.ac.uk")),
                        hasProperty("oidcIdentifierClaims", is(empty()))
                )
        ));

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(100000002L);
        subscriber.ifPresent(s -> this.entityManager.refresh(s));

        Assert.assertTrue(subscriber.isPresent());
        Assert.assertTrue(subscriber.get().getSamlIdentifiers().isEmpty());
        assertThat(subscriber.get().getOidcIdentifiers(), containsInAnyOrder(
                allOf(
                        hasProperty("issuer", is("https://auth.open.ac.uk")),
                        hasProperty("oidcIdentifierClaims", is(empty()))
                )
        ));
    }

    /**
     * Test setOidcIdentifiers when the subscriber doesn't exist.
     */
    @Test
    public void testSetOidcIdentifiersNotFound() {
        Set<OidcIdentifierCommandDto> oidcIdentifiers = Set.of(
                new OidcIdentifierCommandDto("https://accounts.google.com", List.of(
                        new OidcIdentifierClaimCommandDto("sub", "3DA52E3")
                ))
        );
        Optional<SubscriberResponseDto> responseDto = this.subscriberService.setOidcIdentifiers(42L, oidcIdentifiers);

        Assert.assertTrue(responseDto.isEmpty());
    }

    /**
     * Test getSubscriberBySamlIdentifier when one matching subscriber is found.
     */
    @Test
    public void testGetSubscriberBySamlIdentifier() {
        SamlIdentifierRequestDto requestDto
                = new SamlIdentifierRequestDto("https://brighton.ac.uk/shib", "student@brighton.ac.uk");
        Set<SubscriberResponseDto> responseDtos = this.subscriberService.getSubscriberBySamlIdentifier(requestDto);

        assertThat(responseDtos, contains(
                allOf(
                        hasProperty("id", is(100000008L)),
                        hasProperty("subscriberName", is("University of Brighton")),
                        hasProperty("samlIdentifiers", contains(
                                allOf(
                                        hasProperty("entityId", is("https://brighton.ac.uk/shib")),
                                        hasProperty("scopedAffiliation", is("staff@brighton.ac.uk"))
                                ),
                                allOf(
                                        hasProperty("entityId", is("https://brighton.ac.uk/shib")),
                                        hasProperty("scopedAffiliation", is("student@brighton.ac.uk"))
                                )
                        )),
                        hasProperty("oidcIdentifiers", contains(
                                allOf(
                                        hasProperty("issuer", is("https://oidc.brighton.ac.uk")),
                                        hasProperty("oidcIdentifierClaims", contains(
                                                allOf(
                                                        hasProperty("claimName", is("sub")),
                                                        hasProperty("claimValue", is("21224"))
                                                )
                                        ))
                                )
                        ))
                )
        ));
    }

    /**
     * Test getSubscriberBySamlIdentifier when multiple matching subscribers are found.
     */
    @Test
    public void testGetSubscriberBySamlIdentifierMultiple() {
        SamlIdentifierRequestDto requestDto
                = new SamlIdentifierRequestDto("https://brighton.ac.uk/shib", "staff@brighton.ac.uk");
        Set<SubscriberResponseDto> responseDtos = this.subscriberService.getSubscriberBySamlIdentifier(requestDto);

        assertThat(responseDtos, containsInAnyOrder(
                allOf(
                        hasProperty("id", is(100000008L)),
                        hasProperty("subscriberName", is("University of Brighton")),
                        hasProperty("samlIdentifiers", containsInAnyOrder(
                                allOf(
                                        hasProperty("entityId", is("https://brighton.ac.uk/shib")),
                                        hasProperty("scopedAffiliation", is("staff@brighton.ac.uk"))
                                ),
                                allOf(
                                        hasProperty("entityId", is("https://brighton.ac.uk/shib")),
                                        hasProperty("scopedAffiliation", is("student@brighton.ac.uk"))
                                )
                        )),
                        hasProperty("oidcIdentifiers", contains(
                                allOf(
                                        hasProperty("issuer", is("https://oidc.brighton.ac.uk")),
                                        hasProperty("oidcIdentifierClaims", contains(
                                                allOf(
                                                        hasProperty("claimName", is("sub")),
                                                        hasProperty("claimValue", is("21224"))
                                                )
                                        ))
                                ))
                        )),
                        allOf(
                        hasProperty("id", is(100000009L)),
                        hasProperty("subscriberName", is("University of Brighton Admin")),
                        hasProperty("samlIdentifiers", contains(
                                allOf(
                                        hasProperty("entityId", is("https://brighton.ac.uk/shib")),
                                        hasProperty("scopedAffiliation", is("staff@brighton.ac.uk"))
                                )
                        )),
                        hasProperty("oidcIdentifiers", empty())
                )
        ));
    }

    /**
     * Test getSubscriberBySamlIdentifier when zero matching subscribers are found.
     */
    @Test
    public void testGetSubscriberBySamlIdentifierNone() {
        SamlIdentifierRequestDto requestDto
                = new SamlIdentifierRequestDto("https://example.com/shib", "foo@example.com");
        Set<SubscriberResponseDto> responseDtos = this.subscriberService.getSubscriberBySamlIdentifier(requestDto);

        Assert.assertTrue(responseDtos.isEmpty());
    }

    /**
     * Test getSubscriberIdBySubscriberName when a subscriber is found.
     */
    @Test
    public void testGetSubscriberIdBySubscriberName() {
        Optional<Long> subscriberId = this.subscriberService.getSubscriberIdBySubscriberName("Vince Clarke");

        Assert.assertEquals(subscriberId, Optional.of(100000010L));
    }

    /**
     * Test getSubscriberIdBySubscriberName when a subscriber is not found.
     */
    @Test
    public void testGetSubscriberIdBySubscriberNameNotFound() {
        Optional<Long> subscriberId = this.subscriberService.getSubscriberIdBySubscriberName("Robert Dobbs");

        Assert.assertTrue(subscriberId.isEmpty());
    }
}
