package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.io.Serializable;
import java.util.List;

/**
 * Response DTO representing a subscriber.
 */
public class SubscriberResponseDto implements Serializable {

    private final Long id;
    private final String subscriberName;
    private final List<SamlIdentifierResponseDto> samlIdentifierResponseDtos;
    private final List<OidcIdentifierResponseDto> oidcIdentifierResponseDtos;

    /**
     * Constructs a new SubscriberResponseDto using the supplied arguments.
     * @param id the subscriber identifier
     * @param subscriberName the subscriber's name
     * @param samlIdentifierResponseDtos set of SAML identifiers associated with this subscriber
     * @param oidcIdentifierResponseDtos set of OIDC identifiers associated with this subscriber
     */
    public SubscriberResponseDto(final Long id, final String subscriberName,
            final List<SamlIdentifierResponseDto> samlIdentifierResponseDtos,
            final List<OidcIdentifierResponseDto> oidcIdentifierResponseDtos) {
        this.id = id;
        this.subscriberName = subscriberName;
        this.samlIdentifierResponseDtos = samlIdentifierResponseDtos;
        this.oidcIdentifierResponseDtos = oidcIdentifierResponseDtos;
    }

    /**
     * @return the subscriber identifier
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @return the subscriber's name
     */
    public String getSubscriberName() {
        return this.subscriberName;
    }

    /**
     * @return a list of SAML identifiers associated with this subscriber in their natural order
     */
    public List<SamlIdentifierResponseDto> getSamlIdentifiers() {
        return this.samlIdentifierResponseDtos;
    }

    /**
     * @return a list of OIDC identifiers associated with this subscriber in their natural order
     */
    public List<OidcIdentifierResponseDto> getOidcIdentifiers() {
        return this.oidcIdentifierResponseDtos;
    }
}
