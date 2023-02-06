package uk.co.zodiac2000.subscriptionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.zodiac2000.subscriptionmanager.domain.resource.SubscriptionResource;

/**
 * Repository for resource aggregates.
 */
public interface SubscriptionResourceRepository extends JpaRepository<SubscriptionResource, Long> {

}
