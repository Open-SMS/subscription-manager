package uk.co.zodiac2000.subscriptionmanager.service;

import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptionresource.SubscriptionResource;
import uk.co.zodiac2000.subscriptionmanager.factory.SubscriptionResourceFactory;
import uk.co.zodiac2000.subscriptionmanager.factory.SubscriptionResourceResponseDtoFactory;
import uk.co.zodiac2000.subscriptionmanager.repository.SubscriptionResourceRepository;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.NewSubscriptionResourceCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceResponseDto;

/**
 * Service facade for SubscriptionResource aggregates.
 */
@Service
@Transactional(readOnly = true)
public class SubscriptionResourceService {

    @Autowired
    private SubscriptionResourceRepository subscriptionResourceRepository;

    @Autowired
    private SubscriptionResourceResponseDtoFactory subscriptionResourceResponseDtoFactory;

    @Autowired
    private SubscriptionResourceFactory subscriptionResourceFactory;

    /**
     * Returns the subscription resource identified by {@code id} or an empty optional if not found.
     * @param id the subscription resource id
     * @return a subscription resource
     */
    public Optional<SubscriptionResourceResponseDto> getSubscriptionResource(final long id) {
        Optional<SubscriptionResource> resource = this.subscriptionResourceRepository.findById(id);
        return this.subscriptionResourceResponseDtoFactory.subscriptionResourceToResponseDto(resource);
    }

    /**
     * Returns the subscription resource identified by {@code uriString} or an empty optional if not found. If
     * the URI is not valid an IllegalArgumentException is thrown.
     * @param uriString the URI that identifies a subscription resource represented as a string
     * @return a subscription resource
     */
    public Optional<SubscriptionResourceResponseDto> getSubscriptionResourceByUri(final String uriString) {
        URI uri = URI.create(uriString);
        Optional<SubscriptionResource> resource = this.subscriptionResourceRepository.findByResourceUri(uri);
        return this.subscriptionResourceResponseDtoFactory.subscriptionResourceToResponseDto(resource);
    }

    /**
     * Creates a new SubscriptionResource aggregate and persists it. Returns a response DTO representing the new
     * subscription resource.
     * @param commandDto command DTO representing the new subscription resource
     * @return response DTO representing the new subscription
     */
    @Transactional(readOnly = false)
    public Optional<SubscriptionResourceResponseDto> createSubscriptionResource(
            final NewSubscriptionResourceCommandDto commandDto) {
        SubscriptionResource subscriptionResource = this.subscriptionResourceRepository.save(
                this.subscriptionResourceFactory.newSubscriptionResourceCommandDtoToSubscriptionResource(commandDto)
        );
        return this.subscriptionResourceResponseDtoFactory.subscriptionResourceToResponseDto(
                Optional.of(subscriptionResource));
    }
}
