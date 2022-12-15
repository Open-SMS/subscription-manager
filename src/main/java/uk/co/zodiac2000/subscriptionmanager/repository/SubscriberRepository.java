package uk.co.zodiac2000.subscriptionmanager.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.Subscriber;

/**
 * Repository for Subscriber aggregates.
 */
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    /**
     * Returns subscribers associated with at least one OidcIdentifier matching the arguments.
     * @param issuer the iss claim
     * @param subject the sub claim
     * @return a set of identified subscribers
     */
    Set<Subscriber> findByOidcIdentifiersIssuerAndOidcIdentifiersSubject(String issuer, String subject);

    /**
     * Returns subscribers associated with at least one SamlIdentifier matching the arguments.
     * @param entityId the issuer's entityId
     * @param scopedAffiliation the value of the eduPersonScopedAffiliation attribute released by the issuer
     * @return a set of identified subscribers
     */
    Set<Subscriber> findBySamlIdentifiersEntityIdAndSamlIdentifiersScopedAffiliation(String entityId, String scopedAffiliation);
}
