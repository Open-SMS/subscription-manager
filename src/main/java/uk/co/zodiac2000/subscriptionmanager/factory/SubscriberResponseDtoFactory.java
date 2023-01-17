package uk.co.zodiac2000.subscriptionmanager.factory;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.OidcIdentifier;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.SamlIdentifier;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.Subscriber;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierClaimResponseDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierResponseDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SamlIdentifierResponseDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SubscriberResponseDto;

/**
 * Factory that produces subscriber response DTOs.
 */
@Component
public class SubscriberResponseDtoFactory {

    /**
     * @param subscribers a set of subscribers
     * @return the set of subscribers mapped by {@link #subscriberToSubscriberResponseDto}
     */
    public Set<SubscriberResponseDto> subscribersToSubscriberResponseDtos(final Set<Subscriber> subscribers) {
        return subscribers.stream()
                .map(p -> this.subscriberToSubscriberResponseDto(Optional.of(p)).get())
                .collect(Collectors.toSet());
    }

    /**
     * Returns a SubscriberResponseDto based on the state of the subscriber argument. If the optional is empty then
     * an empty optional is returned.
     * @param subscriber the subscriber
     * @return response DTO representation of subscriber
     */
    public Optional<SubscriberResponseDto> subscriberToSubscriberResponseDto(final Optional<Subscriber> subscriber) {
        return subscriber
                .map(p -> {
                    return new SubscriberResponseDto(
                            p.getId(),
                            p.getSubscriberName(),
                            samlIdentifiersToSamlIdentifierResponseDtos(p.getSamlIdentifiers()),
                            oidcIdentifiersToOidcIdentifierResponseDtos(p.getOidcIdentifiers())
                    );
                });
    }

    /**
     * Returns a natural sort ordered list of SamlIdentifierResponseDto objects based on the samlIdentifiers argument.
     * @param samlIdentifiers a set of SAML identifiers
     * @return a set of SamlIdentifierResponseDto objects
     */
    public List<SamlIdentifierResponseDto> samlIdentifiersToSamlIdentifierResponseDtos(
            final Set<SamlIdentifier> samlIdentifiers) {
        return samlIdentifiers.stream()
                .sorted()
                .map(i -> {
                    return new SamlIdentifierResponseDto(
                            i.getEntityId(),
                            i.getScopedAffiliation()
                    );
                })
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of OidcIdentifierResponseDto objects order by issuer. The composed OidcIdentifierClaimResponseDto
     * are sorted in their natural order.
     * @param oidcIdentifiers a set of OIDC identifiers
     * @return a list of OidcIdentifierResponseDto objects
     */
    public List<OidcIdentifierResponseDto> oidcIdentifiersToOidcIdentifierResponseDtos(
            final Set<OidcIdentifier> oidcIdentifiers) {
        return oidcIdentifiers.stream()
                .sorted(Comparator.comparing(OidcIdentifier::getIssuer))
                .map(i -> {
                    return new OidcIdentifierResponseDto(
                            i.getIssuer(),
                            i.getOidcIdentifierClaims().stream()
                                    .sorted()
                                    .map(c -> {
                                        return new OidcIdentifierClaimResponseDto(c.getClaimName(), c.getClaimValue());
                                    })
                            .collect(Collectors.toList())
                    );
                })
                .collect(Collectors.toList());
    }
}
