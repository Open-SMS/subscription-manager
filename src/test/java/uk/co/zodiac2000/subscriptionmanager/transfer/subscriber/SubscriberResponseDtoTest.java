package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit test for SubscriberResponseDto.
 */
public class SubscriberResponseDtoTest {

    private static final Long SUBSCRIBER_ID = 42L;
    private static final String SUBSCRIBER_NAME = "Trevor Horn";
    private static final List<SamlIdentifierResponseDto> SAML_IDENTIFIER_RESPONSE_DTOS = List.of();
    private static final List<OidcIdentifierResponseDto> OIDC_IDENTIFIER_RESPONSE_DTOS = List.of();

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        SubscriberResponseDto subscriber = new SubscriberResponseDto(SUBSCRIBER_ID, SUBSCRIBER_NAME, SAML_IDENTIFIER_RESPONSE_DTOS,
                OIDC_IDENTIFIER_RESPONSE_DTOS);

        Assert.assertEquals(subscriber.getId(), SUBSCRIBER_ID);
        Assert.assertEquals(subscriber.getSubscriberName(), SUBSCRIBER_NAME);
        Assert.assertSame(subscriber.getSamlIdentifiers(), SAML_IDENTIFIER_RESPONSE_DTOS);
        Assert.assertSame(subscriber.getOidcIdentifiers(), OIDC_IDENTIFIER_RESPONSE_DTOS);
    }
}
