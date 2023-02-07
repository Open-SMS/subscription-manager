package uk.co.zodiac2000.subscriptionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.zodiac2000.subscriptionmanager.domain.contentidentifier.ContentIdentifier;

/**
 * Repository for content identifier aggregates.
 */
public interface ContentIdentifierRepository extends JpaRepository<ContentIdentifier, Long> {

}
