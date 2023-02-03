package uk.co.zodiac2000.subscriptionmanager.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    /**
     * Inserts a new row into the claim_name table with the provided claimName argument. If a row already
     * exists with the same claim name then no insert takes place. This prevents concurrent transactions
     * creating duplicate claim names do not result in an exception.
     * @param claimName the claim name
     */
    @Modifying
    @Query(value = "INSERT INTO claim_name (claim_name) VALUES (?) ON CONFLICT DO NOTHING",
            nativeQuery = true)
    void insertIntoClaimName(String claimName);
}
