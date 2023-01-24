package uk.co.zodiac2000.subscriptionmanager.repository;

import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.Subscriber;

/**
 * Repository for Subscriber aggregates.
 */
public interface SubscriberRepository extends JpaRepository<Subscriber, Long>,
        JpaSpecificationExecutor<Subscriber> {

    /**
     * Returns subscribers associated with at least one SamlIdentifier matching the arguments.
     * @param entityId the issuer's entityId
     * @param scopedAffiliation the value of the eduPersonScopedAffiliation attribute released by the issuer
     * @return a set of identified subscribers
     */
    Set<Subscriber> findBySamlIdentifiersEntityIdAndSamlIdentifiersScopedAffiliation(String entityId,
            String scopedAffiliation);

    /**
     * Returns an Optional containing a Subscriber with subscriberName matching the argument, or an empty Optional if
     * none found.
     * @param subscriberName the subscriber name to locate
     * @return an Optional containing a Subscriber with subscriberName matching the argument, or an empty Optional if
     * none found
     */
    Optional<Subscriber> findBySubscriberName(String subscriberName);
}
