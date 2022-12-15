package uk.co.zodiac2000.subscriptionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription;

/**
 * Repository for Subscription aggregates.
 */
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
