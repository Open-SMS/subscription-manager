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
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.UpdateSubscriptionResourceCommandDto;
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
    public Optional<Long> getSubscriptionResourceIdByUri(final String uriString) {
        URI uri = URI.create(uriString);
        return this.subscriptionResourceRepository.findByResourceUri(uri).map(SubscriptionResource::getId);
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

    /**
     * Updates the SubscriptionResource resourceUri and resourceDescription fields of the object identified by
     * id in commandDto. The command DTO contains the updated state of the SubscriptionResource fields. The updated
     * SubscriptionResource is returned. If the SubscriptionResource does not exist then an empty optional is
     * returned.
     * @param commandDto command DTO representing the updated state of the subscription resource
     * @return the updated subscription resource
     */
    @Transactional(readOnly = false)
    public Optional<SubscriptionResourceResponseDto> updateSubscriptionResource(
            final UpdateSubscriptionResourceCommandDto commandDto) {
        Optional<SubscriptionResource> resource = this.subscriptionResourceRepository.findById(commandDto.getId());
        resource.ifPresent(r -> r.updateSubscriptionResource(commandDto));
        return this.subscriptionResourceResponseDtoFactory.subscriptionResourceToResponseDto(resource);
    }

    /**
     * Returns true if the SubscriptionResource object identified by id exists in the system.
     * @param id the subscription resource identifier
     * @return true if the object exists
     */
    public boolean isPresent(final long id) {
        return this.subscriptionResourceRepository.existsById(id);
    }
}
