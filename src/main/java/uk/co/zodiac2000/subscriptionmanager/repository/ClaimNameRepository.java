package uk.co.zodiac2000.subscriptionmanager.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.zodiac2000.subscriptionmanager.domain.oidcclaims.ClaimName;

/**
 * Repository for ClaimName aggregates.
 */
public interface ClaimNameRepository extends JpaRepository<ClaimName, Long> {

    /**
     * Returns the {@code ClaimName} with claim name equal to {@code claimName}.
     * @param claimName the claim name to locate
     * @return a {@code ClaimName} or empty {@code Optional}
     */
    Optional<ClaimName> findByClaimName(String claimName);
}
