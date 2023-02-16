package uk.co.zodiac2000.subscriptionmanager.repository;

import java.net.URI;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptionresource.SubscriptionResource;

/**
 * Repository for resource aggregates.
 */
public interface SubscriptionResourceRepository extends JpaRepository<SubscriptionResource, Long> {

    /**
     * Returns a SubscriptionResource with resourceUri field matching the uri parameter.
     * @param uri the URI
     * @return a SubscriptionResource
     */
    Optional<SubscriptionResource> findByResourceUri(URI uri);
}
