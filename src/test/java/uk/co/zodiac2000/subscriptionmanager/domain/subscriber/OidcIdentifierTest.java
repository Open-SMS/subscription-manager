package uk.co.zodiac2000.subscriptionmanager.domain.subscriber;

import java.util.List;
import java.util.Set;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierClaimRequestDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierClaimValueRequestDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierRequestDto;

/**
 * Unit tests for OidcIdentifier.
 */
public class OidcIdentifierTest {

    private static final Subscriber SUBSCRIBER = new Subscriber("Fred Dobbs");
    private static final String ISSUER_ONE = "https://www.facebook.com";
    private static final String ISSUER_TWO = "https://accounts.google.com";
    private static final String CLAIM_NAME_ONE = "sub";
    private static final String CLAIM_VALUE_ONE = "3204823904";
    private static final String CLAIM_NAME_TWO = "sub";
    private static final String CLAIM_VALUE_TWO = "d2liYmxlCg==";
    private static final Set<OidcIdentifierClaim> OIDC_IDENTIFIER_CLAIMS_ONE = Set.of(
            new OidcIdentifierClaim(CLAIM_NAME_ONE, CLAIM_VALUE_ONE),
            new OidcIdentifierClaim(CLAIM_NAME_TWO, CLAIM_VALUE_TWO)
    );
    private static final String CLAIM_NAME_THREE = "sub";
    private static final String CLAIM_VALUE_THREE = "23201384129";
    private static final String CLAIM_NAME_FOUR = "groups";
    private static final String CLAIM_VALUE_FOUR = "staff";
    private static final Set<OidcIdentifierClaim> OIDC_IDENTIFIER_CLAIMS_TWO = Set.of(
            new OidcIdentifierClaim(CLAIM_NAME_THREE, CLAIM_VALUE_THREE),
            new OidcIdentifierClaim(CLAIM_NAME_FOUR, CLAIM_VALUE_FOUR)
    );

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        OidcIdentifier identifier = new OidcIdentifier();

        Assert.assertNotNull(identifier);
    }

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        OidcIdentifier identifier = new OidcIdentifier(ISSUER_ONE, OIDC_IDENTIFIER_CLAIMS_ONE, SUBSCRIBER);

        Assert.assertEquals(identifier.getIssuer(), ISSUER_ONE);
        Assert.assertEquals(identifier.getOidcIdentifierClaims(), OIDC_IDENTIFIER_CLAIMS_ONE);
        Assert.assertSame(ReflectionTestUtils.getField(identifier, "subscriber"), SUBSCRIBER);
    }

    /**
     * Test constructor when issuer is null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorIssuerNull() {
        OidcIdentifier identifier = new OidcIdentifier(null, Set.of(), SUBSCRIBER);
    }

    /**
     * Test constructor when oidcIdentifierClaims is null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorOidcIdentifierClaimsNull() {
        OidcIdentifier identifier = new OidcIdentifier(ISSUER_ONE, null, SUBSCRIBER);
    }

    /**
     * Test constructor when oidcIdentifierClaims collection is empty.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testConstructorOidcIdentifierClaimsEmpty() {
        OidcIdentifier identifier = new OidcIdentifier(ISSUER_ONE, Set.of(), SUBSCRIBER);
    }

    /**
     * Test equals when argument is null.
     */
    @Test
    public void testEqualsNull() {
        OidcIdentifier identifier = new OidcIdentifier(ISSUER_ONE, OIDC_IDENTIFIER_CLAIMS_ONE, SUBSCRIBER);

        Assert.assertFalse(identifier.equals(null));
    }

    /**
     * Test equals when argument is a different class.
     */
    @Test
    public void testEqualsDifferent() {
        OidcIdentifier identifier = new OidcIdentifier(ISSUER_ONE, OIDC_IDENTIFIER_CLAIMS_ONE, SUBSCRIBER);

        Assert.assertFalse(identifier.equals("foo"));
    }

    /**
     * Test equals when issuer fields are not equal.
     */
    @Test
    public void testEqualsDifferentIssuer() {
        OidcIdentifier identifierOne = new OidcIdentifier(ISSUER_ONE, OIDC_IDENTIFIER_CLAIMS_ONE, SUBSCRIBER);
        OidcIdentifier identifierTwo = new OidcIdentifier(ISSUER_TWO, OIDC_IDENTIFIER_CLAIMS_ONE, SUBSCRIBER);

        Assert.assertFalse(identifierOne.equals(identifierTwo));
    }

    /**
     * Test equals when the issuer fields are equal but oidcIdentifier set contains elements that are not equal.
     */
    @Test
    public void testEqualsDifferentScopedAffiliation() {
        OidcIdentifier identifierOne = new OidcIdentifier(ISSUER_ONE, OIDC_IDENTIFIER_CLAIMS_ONE, SUBSCRIBER);
        OidcIdentifier identifierTwo = new OidcIdentifier(ISSUER_ONE, OIDC_IDENTIFIER_CLAIMS_TWO, SUBSCRIBER);

        Assert.assertFalse(identifierOne.equals(identifierTwo));
    }

    /**
     * Test equals when objects are equal.
     */
    @Test
    public void testEquals() {
        OidcIdentifier identifierOne = new OidcIdentifier(ISSUER_ONE, OIDC_IDENTIFIER_CLAIMS_ONE, SUBSCRIBER);
        OidcIdentifier identifierTwo = new OidcIdentifier(ISSUER_ONE, OIDC_IDENTIFIER_CLAIMS_ONE, SUBSCRIBER);

        Assert.assertTrue(identifierOne.equals(identifierTwo));
    }

    /**
     * Test hashcode.
     */
    @Test
    public void testHashCode() {
        OidcIdentifier identifierOne = new OidcIdentifier(ISSUER_ONE, OIDC_IDENTIFIER_CLAIMS_ONE, SUBSCRIBER);
        OidcIdentifier identifierTwo = new OidcIdentifier(ISSUER_ONE, OIDC_IDENTIFIER_CLAIMS_ONE, SUBSCRIBER);

        Assert.assertEquals(identifierOne.hashCode(), identifierTwo.hashCode());
    }

    /**
     * Test removeSubscriber.
     */
    @Test
    public void testRemoveSubscriber() {
        OidcIdentifier identifier = new OidcIdentifier(ISSUER_ONE, OIDC_IDENTIFIER_CLAIMS_ONE, SUBSCRIBER);
        identifier.removeSubscriber();

        Assert.assertNull(ReflectionTestUtils.getField(identifier, "subscriber"));
    }

    /**
     * Test claimsSatisfyRequirements where the issuer associated with the request does not match the issuer
     * associated with the OidcIdentifier.
     */
    @Test
    public void testClaimsSatisfyRequirementsDifferentIssuer() {
        OidcIdentifier identifier = new OidcIdentifier(ISSUER_ONE, OIDC_IDENTIFIER_CLAIMS_ONE, SUBSCRIBER);
        OidcIdentifierRequestDto request = new OidcIdentifierRequestDto(ISSUER_TWO, List.of());

        Assert.assertFalse(identifier.claimsSatisfyRequirements(request));
    }

    /**
     * Test claimsSatisfyRequirements where the issuer associated with the request matches the issuer
     * associated with the OidcIdentifier but the claims do not.
     */
    @Test
    public void testClaimsSatisfyRequirementsDifferentClaims() {
        OidcIdentifier identifier = new OidcIdentifier(ISSUER_ONE, OIDC_IDENTIFIER_CLAIMS_ONE, SUBSCRIBER);
        OidcIdentifierRequestDto request = new OidcIdentifierRequestDto(ISSUER_ONE, List.of(
                new OidcIdentifierClaimRequestDto("FOO", List.of())
        ));

        Assert.assertFalse(identifier.claimsSatisfyRequirements(request));
    }

    /**
     * Test claimsSatisfyRequirements where the issuer associated with the request matches the issuer
     * associated with the OidcIdentifier and the request contains claims which match all the required
     * claims.
     */
    @Test
    public void testClaimsSatisfyRequirements() {
        OidcIdentifier identifier = new OidcIdentifier(ISSUER_ONE, OIDC_IDENTIFIER_CLAIMS_ONE, SUBSCRIBER);
        OidcIdentifierRequestDto request = new OidcIdentifierRequestDto(ISSUER_ONE, List.of(
                new OidcIdentifierClaimRequestDto(CLAIM_NAME_ONE, List.of(
                        new OidcIdentifierClaimValueRequestDto(CLAIM_VALUE_ONE)
                )),
                new OidcIdentifierClaimRequestDto(CLAIM_NAME_TWO, List.of(
                        new OidcIdentifierClaimValueRequestDto(CLAIM_VALUE_TWO)
                ))
        ));

        Assert.assertTrue(identifier.claimsSatisfyRequirements(request));
    }
}
