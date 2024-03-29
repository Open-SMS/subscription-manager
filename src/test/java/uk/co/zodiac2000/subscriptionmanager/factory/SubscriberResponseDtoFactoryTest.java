package uk.co.zodiac2000.subscriptionmanager.factory;

import java.util.Optional;
import java.util.Set;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.OidcIdentifier;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.OidcIdentifierClaim;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.SamlIdentifier;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.Subscriber;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SubscriberResponseDto;

/**
 * Unit tests for SubscriberResponseDtoFactory.
 */
public class SubscriberResponseDtoFactoryTest {

    private static final Long SUBSCRIBER_ID = 42L;
    private static final String SUBSCRIBER_NAME = "Terry Riley";
    private static final Subscriber SUBSCRIBER = new Subscriber("SUBSCRIBER_NAME");

    private static final Long IDENTIFIERS_SUBSCRIBER_ID = 67L;
    private static final String IDENTIFIERS_SUBSCRIBER_NAME = "Durham University";
    private static final String ENTITY_ID_ONE = "urn:mace:ac.uk:sdss.ac.uk:provider:identity:dur.ac.uk";
    private static final String ENTITY_ID_TWO = "https://idp.cardiff.ac.uk/shibboleth";
    private static final String SCOPED_AFFILIATION_ONE = "staff@cardiff.ac.uk";
    private static final String SCOPED_AFFILIATION_TWO = "member@dur.ac.uk";
    private static final Set<SamlIdentifier> SAML_IDENTIFIERS = Set.of(
            new SamlIdentifier(ENTITY_ID_ONE, SCOPED_AFFILIATION_ONE),
            new SamlIdentifier(ENTITY_ID_TWO, SCOPED_AFFILIATION_TWO)
    );
    private static final String ISSUER_ONE = "https://www.facebook.com";
    private static final String ISSUER_TWO = "https://accounts.google.com";
    private static final String CLAIM_NAME_ONE = "sub";
    private static final String CLAIM_VALUE_ONE = "3204823904";
    private static final String CLAIM_NAME_TWO = "sub";
    private static final String CLAIM_VALUE_TWO = "d2liYmxlCg==";
    private static final String CLAIM_NAME_THREE = "sub";
    private static final String CLAIM_VALUE_THREE = "23201384129";
    private static final String CLAIM_NAME_FOUR = "groups";
    private static final String CLAIM_VALUE_FOUR = "staff";

    private static final Set<OidcIdentifier> OIDC_IDENTIFIERS = Set.of(
            new OidcIdentifier(ISSUER_ONE, Set.of(
                    new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_ONE),
                    new OidcIdentifierClaim(CLAIM_NAME_TWO, CLAIM_VALUE_TWO)
            ), SUBSCRIBER),
            new OidcIdentifier(ISSUER_TWO, Set.of(
                    new OidcIdentifierClaim(CLAIM_NAME_THREE, CLAIM_VALUE_THREE),
                    new OidcIdentifierClaim(CLAIM_NAME_FOUR, CLAIM_VALUE_FOUR)
            ), SUBSCRIBER)
    );

    private final SubscriberResponseDtoFactory factory = new SubscriberResponseDtoFactory();

    private Subscriber subscriber;
    private Subscriber identifiersSubscriber;

    /**
     * Set up the subscriber test fixture.
     */
    @BeforeMethod
    public void setUpSubscriber() {
        this.subscriber = new Subscriber(SUBSCRIBER_NAME);
        ReflectionTestUtils.setField(this.subscriber, "id", SUBSCRIBER_ID);

        this.identifiersSubscriber = new Subscriber(IDENTIFIERS_SUBSCRIBER_NAME);
        ReflectionTestUtils.setField(this.identifiersSubscriber, "id", IDENTIFIERS_SUBSCRIBER_ID);
        ReflectionTestUtils.setField(this.identifiersSubscriber, "samlIdentifiers", SAML_IDENTIFIERS);
        ReflectionTestUtils.setField(this.identifiersSubscriber, "oidcIdentifiers", OIDC_IDENTIFIERS);

    }

    /**
     * Test subscriberToSubscriberResponseDto when the subscriber is not associated with any identifiers.
     */
    @Test
    public void testSubscriberToSubscriberResponseDto() {
        Optional<SubscriberResponseDto> responseDto = this.factory.subscriberToSubscriberResponseDto(Optional.of(this.subscriber));

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), is(
                allOf(
                        hasProperty("id", is(SUBSCRIBER_ID)),
                        hasProperty("subscriberName", is(SUBSCRIBER_NAME)),
                        hasProperty("samlIdentifiers", empty()),
                        hasProperty("oidcIdentifiers", empty())
                )
        ));
    }

    /**
     * Test subscriberToSubscriberResponseDto when the subscriber is associated with identifiers.
     */
    @Test
    public void testSubscriberToSubscriberResponseDtoIdentifiers() {
        Optional<SubscriberResponseDto> responseDto
                = this.factory.subscriberToSubscriberResponseDto(Optional.of(this.identifiersSubscriber));

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), is(
                allOf(
                        hasProperty("id", is(IDENTIFIERS_SUBSCRIBER_ID)),
                        hasProperty("subscriberName", is(IDENTIFIERS_SUBSCRIBER_NAME)),
                        hasProperty("samlIdentifiers", contains(
                                allOf(
                                        hasProperty("entityId", is(ENTITY_ID_TWO)),
                                        hasProperty("scopedAffiliation", is(SCOPED_AFFILIATION_TWO))
                                ),
                                allOf(
                                        hasProperty("entityId", is(ENTITY_ID_ONE)),
                                        hasProperty("scopedAffiliation", is(SCOPED_AFFILIATION_ONE))
                                )
                        )),
                        hasProperty("oidcIdentifiers", contains(
                                allOf(
                                        hasProperty("issuer", is(ISSUER_TWO)),
                                        hasProperty("oidcIdentifierClaims", contains(
                                                allOf(
                                                        hasProperty("claimName", is(CLAIM_NAME_FOUR)),
                                                        hasProperty("claimValue", is(CLAIM_VALUE_FOUR))
                                                ),
                                                allOf(
                                                        hasProperty("claimName", is(CLAIM_NAME_THREE)),
                                                        hasProperty("claimValue", is(CLAIM_VALUE_THREE))
                                                )
                                        ))
                                ),
                                allOf(
                                        hasProperty("issuer", is(ISSUER_ONE)),
                                        hasProperty("oidcIdentifierClaims", contains(
                                                allOf(
                                                        hasProperty("claimName", is(CLAIM_NAME_ONE)),
                                                        hasProperty("claimValue", is(CLAIM_VALUE_ONE))
                                                ),
                                                allOf(
                                                        hasProperty("claimName", is(CLAIM_NAME_TWO)),
                                                        hasProperty("claimValue", is(CLAIM_VALUE_TWO))
                                                )
                                        ))
                                )
                        ))
                )
        ));
    }

    /**
     * Test subscriberToSubscriberResponseDto when the Optional is empty.
     */
    @Test
    public void testSubscriberToSubscriberResponseDtoEmpty() {
        Optional<SubscriberResponseDto> responseDto = Optional.empty();

        Assert.assertTrue(responseDto.isEmpty());
    }

    /**
     * Test subscribersToSubscriberResponseDtos.
     */
    @Test
    public void testSubscribersToSubscriberResponseDtos() {
        Set<SubscriberResponseDto> responseDtos
                = this.factory.subscribersToSubscriberResponseDtos(Set.of(this.subscriber, this.identifiersSubscriber));

        assertThat(responseDtos, containsInAnyOrder(
                allOf(
                        hasProperty("id", is(SUBSCRIBER_ID)),
                        hasProperty("subscriberName", is(SUBSCRIBER_NAME)),
                        hasProperty("samlIdentifiers", empty()),
                        hasProperty("oidcIdentifiers", empty())
                ),
                allOf(
                        hasProperty("id", is(IDENTIFIERS_SUBSCRIBER_ID)),
                        hasProperty("subscriberName", is(IDENTIFIERS_SUBSCRIBER_NAME)),
                        hasProperty("samlIdentifiers", contains(
                                allOf(
                                        hasProperty("entityId", is(ENTITY_ID_TWO)),
                                        hasProperty("scopedAffiliation", is(SCOPED_AFFILIATION_TWO))
                                ),
                                allOf(
                                        hasProperty("entityId", is(ENTITY_ID_ONE)),
                                        hasProperty("scopedAffiliation", is(SCOPED_AFFILIATION_ONE))
                                )
                        )),
                        hasProperty("oidcIdentifiers", contains(
                                allOf(
                                        hasProperty("issuer", is(ISSUER_TWO)),
                                        hasProperty("oidcIdentifierClaims", contains(
                                                allOf(
                                                        hasProperty("claimName", is(CLAIM_NAME_FOUR)),
                                                        hasProperty("claimValue", is(CLAIM_VALUE_FOUR))
                                                ),
                                                allOf(
                                                        hasProperty("claimName", is(CLAIM_NAME_THREE)),
                                                        hasProperty("claimValue", is(CLAIM_VALUE_THREE))
                                                )
                                        ))
                                ),
                                allOf(
                                        hasProperty("issuer", is(ISSUER_ONE)),
                                        hasProperty("oidcIdentifierClaims", contains(
                                                allOf(
                                                        hasProperty("claimName", is(CLAIM_NAME_ONE)),
                                                        hasProperty("claimValue", is(CLAIM_VALUE_ONE))
                                                ),
                                                allOf(
                                                        hasProperty("claimName", is(CLAIM_NAME_TWO)),
                                                        hasProperty("claimValue", is(CLAIM_VALUE_TWO))
                                                )
                                        ))
                                )
                        ))
                )
        ));
    }
}
