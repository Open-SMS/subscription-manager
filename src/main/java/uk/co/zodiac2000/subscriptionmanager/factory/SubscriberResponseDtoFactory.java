package uk.co.zodiac2000.subscriptionmanager.factory;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.OidcIdentifier;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.SamlIdentifier;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.Subscriber;
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
    public Set<SubscriberResponseDto> subscribersToSubscriberResponseDtos(Set<Subscriber> subscribers) {
        return subscribers.stream() // Is this a candidate for parallel? Should I think about using  streams from the repo?
                .map(p -> this.subscriberToSubscriberResponseDto(Optional.of(p)).get())
                .collect(Collectors.toSet());
    }

    /**
     * Returns a SubscriberResponseDto based on the state of the subscriber argument. If the optional is empty then
     * an empty optional is returned.
     * @param subscriber the subscriber
     * @return response DTO representation of subscriber
     */
    public Optional<SubscriberResponseDto> subscriberToSubscriberResponseDto(Optional<Subscriber> subscriber) {
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
    public List<SamlIdentifierResponseDto> samlIdentifiersToSamlIdentifierResponseDtos(Set<SamlIdentifier> samlIdentifiers) {
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
     * Returns a natural sort ordered list of OidcIdentifierResponseDto objects based on the oidcIdentifiers argument.
     * @param oidcIdentifiers a set of SAML identifiers
     * @return a set of OidcIdentifierResponseDto objects
     */
    public List<OidcIdentifierResponseDto> oidcIdentifiersToOidcIdentifierResponseDtos(Set<OidcIdentifier> oidcIdentifiers) {
        return oidcIdentifiers.stream()
                .sorted()
                .map(i -> {
                    return new OidcIdentifierResponseDto(
                            i.getIssuer(),
                            i.getSubject()
                    );
                })
                .collect(Collectors.toList());
    }
}
