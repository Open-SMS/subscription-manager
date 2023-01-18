package uk.co.zodiac2000.subscriptionmanager.domain.subscriber;

import java.util.List;
import java.util.Set;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierClaimCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SamlIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SubscriberNameCommandDto;

/**
 * Unit tests for Subscriber.
 */
public class SubscriberTest {

    private static final Long SUBSCRIBER_ID = 65L;
    private static final String SUBSCRIBER_NAME = "Open University";
    private static final String ENTITY_ID = "urn:mace:ac.uk:sdss.ac.uk:provider:identity:dur.ac.uk";
    private static final String SCOPED_AFFILIATION = "staff@cardiff.ac.uk";
    private static final SamlIdentifierCommandDto SAML_IDENTIFIER = new SamlIdentifierCommandDto(ENTITY_ID, SCOPED_AFFILIATION);
    private static final Set<SamlIdentifierCommandDto> SAML_IDENTIFIERS = Set.of(SAML_IDENTIFIER);
    private static final String ISSUER = "https://accounts.google.com";
    private static final String CLAIM_NAME = "sub";
    private static final String CLAIM_VALUE = "3204823904";
    private static final List<OidcIdentifierClaimCommandDto> OIDC_IDENTIFIER_CLAIMS = List.of(
            new OidcIdentifierClaimCommandDto(CLAIM_NAME, CLAIM_VALUE)
    );
    private static final OidcIdentifierCommandDto OIDC_IDENTIFIER
            = new OidcIdentifierCommandDto(ISSUER, OIDC_IDENTIFIER_CLAIMS);
    private static final Set<OidcIdentifierCommandDto> OIDC_IDENTIFIERS = Set.of(OIDC_IDENTIFIER);

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        Subscriber subscriber = new Subscriber(SUBSCRIBER_NAME);
        ReflectionTestUtils.setField(subscriber, "id", SUBSCRIBER_ID);

        Assert.assertEquals(subscriber.getId(), SUBSCRIBER_ID);
        Assert.assertEquals(subscriber.getSubscriberName(), SUBSCRIBER_NAME);
    }

    /**
     * Test setSubscriberName.
     */
    @Test
    public void testSetSubscriberName() {
        Subscriber subscriber = new Subscriber();
        subscriber.setSubscriberName(new SubscriberNameCommandDto(SUBSCRIBER_ID, SUBSCRIBER_NAME));

        Assert.assertEquals(subscriber.getSubscriberName(), SUBSCRIBER_NAME);
    }

    /**
     * Test SAML identifier accessors.
     */
    @Test
    public void testSamlIdentifierAccessors() {
        Subscriber subscriber = new Subscriber();
        subscriber.setSamlIdentifiers(SAML_IDENTIFIERS);

        assertThat(subscriber.getSamlIdentifiers(), contains(
                equalTo(new SamlIdentifier(ENTITY_ID, SCOPED_AFFILIATION))
        ));
    }

    /**
     * Test OIDC identifier accessors.
     */
    @Test
    public void testOidcIdentifierAccessors() {
        Subscriber subscriber = new Subscriber();
        subscriber.setOidcIdentifiers(OIDC_IDENTIFIERS);

        assertThat(subscriber.getOidcIdentifiers(), contains(
                allOf(
                        hasProperty("issuer", is(ISSUER)),
                        hasProperty("oidcIdentifierClaims", contains(
                                allOf(
                                        hasProperty("claimName", is(CLAIM_NAME)),
                                        hasProperty("claimValue", is(CLAIM_VALUE))
                                )
                        ))
                )
        ));
    }
}
