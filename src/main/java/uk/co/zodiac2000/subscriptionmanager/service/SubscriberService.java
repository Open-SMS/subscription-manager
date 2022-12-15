package uk.co.zodiac2000.subscriptionmanager.service;

import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.Subscriber;
import uk.co.zodiac2000.subscriptionmanager.factory.SubscriberFactory;
import uk.co.zodiac2000.subscriptionmanager.factory.SubscriberResponseDtoFactory;
import uk.co.zodiac2000.subscriptionmanager.repository.SubscriberRepository;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.NewSubscriberCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierRequestDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SamlIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SamlIdentifierRequestDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SubscriberNameCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SubscriberResponseDto;

/**
 * Service facade for Subscriber aggregates.
 */
@Service
@Transactional(readOnly = true)
public class SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SubscriberResponseDtoFactory subscriberResponseDtoFactory;

    @Autowired
    private SubscriberFactory subscriberFactory;

    /**
     * Returns the Subscriber identified by id.
     * @param id the subscriber id
     * @return an Optional that contains a SubscriberResponseDto, if found
     */
    public Optional<SubscriberResponseDto> getSubscriberById(Long id) {
        Optional<Subscriber> subscriber = this.subscriberRepository.findById(id);
        return this.subscriberResponseDtoFactory.subscriberToSubscriberResponseDto(subscriber);
    }

    /**
     * Returns subscribers associated with at least one OidcIdentifier matching the arguments.
     * @param requestDto the OIDC claims being authorized
     * @return a set of SubscriberResponseDto objects
     */
    public Set<SubscriberResponseDto> getSubscriberByOidcIdentifier(OidcIdentifierRequestDto requestDto) {
        Set<Subscriber> subscribers = this.subscriberRepository.findByOidcIdentifiersIssuerAndOidcIdentifiersSubject(
                requestDto.getIssuer(), requestDto.getSubject());
        return this.subscriberResponseDtoFactory.subscribersToSubscriberResponseDtos(subscribers);
    }

    /**
     * Returns subscribers associated with at least one SamlIdentifier matching the arguments.
     * @param requestDto the SAML assertions being authorized
     * @return a set of SubscriberResponseDto objects
     */
    public Set<SubscriberResponseDto> getSubscriberBySamlIdentifier(SamlIdentifierRequestDto requestDto) {
        Set<Subscriber> subscribers = this.subscriberRepository.findBySamlIdentifiersEntityIdAndSamlIdentifiersScopedAffiliation(
                requestDto.getEntityId(), requestDto.getScopedAffiliation());
        return this.subscriberResponseDtoFactory.subscribersToSubscriberResponseDtos(subscribers);
    }

    /**
     * Creates a new Subscriber aggregate root and persists it. Returns a response DTO representing the new subscriber.
     * @param newSubscriberCommandDto
     * @return a SubscriberResponseDto
     */
    @Transactional(readOnly = false)
    public Optional<SubscriberResponseDto> createSubscriber(NewSubscriberCommandDto newSubscriberCommandDto) {
        Subscriber subscriber
                = this.subscriberRepository.save(this.subscriberFactory.newSubscriberCommandDtoToSubscriber(newSubscriberCommandDto));
        return this.subscriberResponseDtoFactory.subscriberToSubscriberResponseDto(Optional.of(subscriber));
    }

    /**
     * Updates the subscriber name of the subscriber identified by id. If the subscriber doesn't exist then an empty optional
     * is returned and no change is made to the state of the system.
     * @param id the subscriber id
     * @param subscriberNameCommandDto the new subscriber name
     * @return an Optional that contains a SubscriberResponseDto representing the new state of the subscriber
     */
    @Transactional(readOnly = false)
    public Optional<SubscriberResponseDto> updateSubscriberName(long id, SubscriberNameCommandDto subscriberNameCommandDto) {
        Optional<Subscriber> subscriber = this.subscriberRepository.findById(id);
        subscriber.ifPresent(p -> p.setSubscriberName(subscriberNameCommandDto));
        return this.subscriberResponseDtoFactory.subscriberToSubscriberResponseDto(subscriber);
    }

    /**
     * Deletes the subscriber identified by id.
     * @param id the subscriber identifier
     */
    @Transactional(readOnly = false)
    public void deleteSubscriber(long id) {
        this.subscriberRepository.findById(id).ifPresent(this.subscriberRepository::delete);
    }

    /**
     * Sets the SAML Identifiers associated with the subscriber identified by id. If the subscriber doesn't exist then an
     * empty optional is returned and no change is made to the state of the system.
     * @param id the subscriber id
     * @param samlIdentifierCommandDtos the new set of SAML identifiers
     * @return an Optional that contains a SubscriberResponseDto representing the new state of the subscriber
     */
    @Transactional(readOnly = false)
    public Optional<SubscriberResponseDto> setSamlIdentifiers(long id, Set<SamlIdentifierCommandDto> samlIdentifierCommandDtos) {
        Optional<Subscriber> subscriber = this.subscriberRepository.findById(id);
        subscriber.ifPresent(p -> p.setSamlIdentifiers(samlIdentifierCommandDtos));
        return this.subscriberResponseDtoFactory.subscriberToSubscriberResponseDto(subscriber);
    }

    /**
     * Sets the OIDC Identifiers associated with the subscriber identified by id. If the subscriber doesn't exist then an
     * empty optional is returned and no change is made to the state of the system.
     * @param id the subscriber id
     * @param oidcIdentifierCommandDtos the new set of OIDC identifiers
     * @return an Optional that contains a SubscriberResponseDto representing the new state of the subscriber
     */
    @Transactional(readOnly = false)
    public Optional<SubscriberResponseDto> setOidcIdentifiers(long id, Set<OidcIdentifierCommandDto> oidcIdentifierCommandDtos ) {
        Optional<Subscriber> subscriber = this.subscriberRepository.findById(id);
        subscriber.ifPresent(p -> p.setOidcIdentifiers(oidcIdentifierCommandDtos));
        return this.subscriberResponseDtoFactory.subscriberToSubscriberResponseDto(subscriber);
    }
}
