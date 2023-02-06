package uk.co.zodiac2000.subscriptionmanager.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.zodiac2000.subscriptionmanager.domain.resource.SubscriptionResource;
import uk.co.zodiac2000.subscriptionmanager.factory.SubscriptionResourceResponseDtoFactory;
import uk.co.zodiac2000.subscriptionmanager.repository.SubscriptionResourceRepository;
import uk.co.zodiac2000.subscriptionmanager.transfer.resource.SubscriptionResourceResponseDto;

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
    /**
     * Returns the resource identified by {@code id} or an empty optional if not found.
     * @param id the subscription resource id
     * @return an subscription resource
     */
    public Optional<SubscriptionResourceResponseDto> getSubscriptionResource(final long id) {
        Optional<SubscriptionResource> resource = this.subscriptionResourceRepository.findById(id);
        return this.subscriptionResourceResponseDtoFactory.subscriptionResourceToResponseDto(resource);
    }
}
