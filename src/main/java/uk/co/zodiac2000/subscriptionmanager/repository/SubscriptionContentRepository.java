package uk.co.zodiac2000.subscriptionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent.SubscriptionContent;

/**
 * Repository for subscription content aggregates.
 */
public interface SubscriptionContentRepository extends JpaRepository<SubscriptionContent, Long> {

}
