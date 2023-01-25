package uk.co.zodiac2000.subscriptionmanager.service;

import java.util.List;
import java.util.Set;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierClaimRequestDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierClaimValueRequestDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierRequestDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SubscriberResponseDto;

/**
 *
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration.properties")
public class AuthorizationServiceTestITCase extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private AuthorizationService authorizationService;

    @BeforeMethod
    public void loadTestData() {
        executeSqlScript("classpath:test_data/claim_name_test_data.sql", false);
        executeSqlScript("classpath:test_data/subscriber_test_data.sql", false);
    }

    /**
     * Test getSubscribersByOidcIdentifiers where the issuer and sub claim in the request identify one subscriber;
     * 100000001, Daphne Oram.
     */
    @Test
    public void testGetSubscribersByOidcIdentifiers() {
        OidcIdentifierRequestDto requestDto = new OidcIdentifierRequestDto("https://www.facebook.com",
                List.of(
                        new OidcIdentifierClaimRequestDto("sub", List.of(
                                new OidcIdentifierClaimValueRequestDto("23987283")
                        ))
                )
        );

        Set<SubscriberResponseDto> subscribers = this.authorizationService.getSubscribersByOidcIdentifiers(requestDto);

        assertThat(subscribers, contains(
                hasProperty("id", is(100000001L))
        ));
    }

    /**
     * Test getSubscribersByOidcIdentifiers when the issuer and claims in the request identify one subscriber;
     * 100000008, University of Brighton. The request contains three claims. The sub claim identifies the subscriber,
     * there are no subscribers associated with the groups claim and issuer, the brighton-acc-id claim name is not
     * known to the system.
     */
    @Test
    public void testGetSubscribersByOidcIdentifiersAdditionalClaims() {
        OidcIdentifierRequestDto requestDto = new OidcIdentifierRequestDto("https://oidc.brighton.ac.uk",
                List.of(
                        new OidcIdentifierClaimRequestDto("sub", List.of(
                                new OidcIdentifierClaimValueRequestDto("21224")
                        )),
                        new OidcIdentifierClaimRequestDto("groups", List.of(
                                new OidcIdentifierClaimValueRequestDto("staff")
                        )),
                        new OidcIdentifierClaimRequestDto("brighton-acc-id", List.of(
                                new OidcIdentifierClaimValueRequestDto("347E28A2B")
                        ))
                )
        );

        Set<SubscriberResponseDto> subscribers = this.authorizationService.getSubscribersByOidcIdentifiers(requestDto);

        assertThat(subscribers, contains(
                hasProperty("id", is(100000008L))
        ));
    }

    /**
     * Test getSubscribersByOidcIdentifiers when the issuer and sub claim in the request identify two subscribers;
     * 100000006, Klaus Schulze and 100000007, Klaus Schulze (work).
     */
    @Test
    public void testGetSubscribersByOidcIdentifiersMultipleSubscribers() {
        OidcIdentifierRequestDto requestDto = new OidcIdentifierRequestDto("https://www.facebook.com",
                List.of(
                        new OidcIdentifierClaimRequestDto("sub", List.of(
                                new OidcIdentifierClaimValueRequestDto("1241482")
                        ))
                )
        );

        Set<SubscriberResponseDto> subscribers = this.authorizationService.getSubscribersByOidcIdentifiers(requestDto);

        assertThat(subscribers, containsInAnyOrder(
                hasProperty("id", is(100000006L)),
                hasProperty("id", is(100000007L))
        ));
    }

    /**
     * Test getSubscribersByOidcIdentifiers when the issuer and claims in the request identify one subscriber;
     * 100000013, Balsdean Library. This subscriber requires two claims to be matched with claim names groups and
     * branch-id.
     */
    @Test
    public void testGetSubscribersByOidcIdentifiersMultipleRequiredClaims() {
        OidcIdentifierRequestDto requestDto = new OidcIdentifierRequestDto("https://brighton-libraries.gov.uk",
                List.of(
                        new OidcIdentifierClaimRequestDto("groups", List.of(
                                new OidcIdentifierClaimValueRequestDto("member")
                        )),
                        new OidcIdentifierClaimRequestDto("branch-id", List.of(
                                new OidcIdentifierClaimValueRequestDto("balsdean")
                        ))
                )
        );

        Set<SubscriberResponseDto> subscribers = this.authorizationService.getSubscribersByOidcIdentifiers(requestDto);

        assertThat(subscribers, contains(
                hasProperty("id", is(100000013L))
        ));
    }

    /**
     * Test getSubscribersByOidcIdentifiers when the issuer and claims in the request identify one subscriber;
     * 100000015, Westdene Library. This subscriber requires the groups: staff claim to be matched, and the
     * request contains two values for this claim one of which matches.
     */
    @Test
    public void testGetSubscribersByOidcIdentifiersMultipleSuppliedClaimValues() {
        OidcIdentifierRequestDto requestDto = new OidcIdentifierRequestDto("https://brighton-libraries.gov.uk",
                List.of(
                        new OidcIdentifierClaimRequestDto("groups", List.of(
                                new OidcIdentifierClaimValueRequestDto("member"),
                                new OidcIdentifierClaimValueRequestDto("staff")
                        )),
                        new OidcIdentifierClaimRequestDto("branch-id", List.of(
                                new OidcIdentifierClaimValueRequestDto("westdene")
                        ))
                )
        );

        Set<SubscriberResponseDto> subscribers = this.authorizationService.getSubscribersByOidcIdentifiers(requestDto);

        assertThat(subscribers, contains(
                hasProperty("id", is(100000015L))
        ));
    }

    /**
     * Test getSubscribersByOidcIdentifiers when the issuer and claims in the request identify one subscriber;
     * 100000016, Brighton Libraries Admin. This subscriber requires the groups: staff and groups: admin claims to be
     * matched, and the request contains three values for this claim two of which match.
     */
    @Test
    public void testGetSubscribersByOidcIdentifiersMultipleSuppliedAndRequiredClaimValues() {
        OidcIdentifierRequestDto requestDto = new OidcIdentifierRequestDto("https://brighton-libraries.gov.uk",
                List.of(
                        new OidcIdentifierClaimRequestDto("groups", List.of(
                                new OidcIdentifierClaimValueRequestDto("admin"),
                                new OidcIdentifierClaimValueRequestDto("hr")
                        )),
                        new OidcIdentifierClaimRequestDto("branch-id", List.of(
                                new OidcIdentifierClaimValueRequestDto("jubilee")
                        ))
                )
        );

        Set<SubscriberResponseDto> subscribers = this.authorizationService.getSubscribersByOidcIdentifiers(requestDto);

        assertThat(subscribers, contains(
                hasProperty("id", is(100000016L))
        ));
    }

    /**
     * Test getSubscribersByOidcIdentifiers when the issuer and claims in the request identify zero subscribers.
     * The request includes an issuer and one claim that identifies a subscriber, but the other OIDC identifier
     * claim associated with this subscriber is not matched.
     */
    @Test
    public void testGetSubscribersByOidcIdentifiersMultipleRequiredClaimsOneNotMatched() {
        OidcIdentifierRequestDto requestDto = new OidcIdentifierRequestDto("https://brighton-libraries.gov.uk",
                List.of(
                        new OidcIdentifierClaimRequestDto("groups", List.of(
                                new OidcIdentifierClaimValueRequestDto("admin")
                        )),
                        new OidcIdentifierClaimRequestDto("branch-id", List.of(
                                new OidcIdentifierClaimValueRequestDto("balsdean")
                        ))
                )
        );

        Set<SubscriberResponseDto> subscribers = this.authorizationService.getSubscribersByOidcIdentifiers(requestDto);

        Assert.assertTrue(subscribers.isEmpty());
    }

    /**
     * Test getSubscribersByOidcIdentifiers when the issuer and claims in the request do not identify any subscribers.
     */
    @Test
    public void testGetSubscribersByOidcIdentifiersNotFound() {
        OidcIdentifierRequestDto requestDto = new OidcIdentifierRequestDto("https://madeira-rr.org.uk",
                List.of(
                        new OidcIdentifierClaimRequestDto("groups", List.of(
                                new OidcIdentifierClaimValueRequestDto("staff")
                        )),
                        new OidcIdentifierClaimRequestDto("branch-id", List.of(
                                new OidcIdentifierClaimValueRequestDto("balsdean")
                        ))
                )
        );

        Set<SubscriberResponseDto> subscribers = this.authorizationService.getSubscribersByOidcIdentifiers(requestDto);

        Assert.assertTrue(subscribers.isEmpty());
    }
}
