package uk.co.zodiac2000.subscriptionmanager.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.zodiac2000.subscriptionmanager.domain.oidcclaims.ClaimName;
import uk.co.zodiac2000.subscriptionmanager.repository.ClaimNameRepository;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierRequestDto;

/**
 * Service facade for {@code ClaimName} aggregates.
 */
@Service
@Transactional(readOnly = true)
public class ClaimNameService {

    @Autowired
    private ClaimNameRepository repository;

    /**
     * Creates a {@code ClaimName} with {@code claimName} as its claim name if it does not already exist.
     * @param claimName the claim name to check
     */
    @Transactional(readOnly = false)
    public void ensurePresent(final String claimName) {
        Optional<ClaimName> claimNameObject = this.repository.findByClaimName(claimName);
        if (claimNameObject.isEmpty()) {
            ClaimName newClaimName = new ClaimName(claimName);
            this.repository.save(newClaimName);
        }
    }

    /**
     * Returns an OidcIdentifierRequestDto based on the state of the oidcIdentifierRequest argument but with any
     * claims where the claim name is unknown to the system removed.
     * @param oidcIdentifierRequest the request to be filtered
     * @return an OidcIdentifierRequestDto containing only claims with names known to the system
     */
    public OidcIdentifierRequestDto filterClaimsRequest(final OidcIdentifierRequestDto oidcIdentifierRequest) {
        Set<String> claimNames = this.repository.findAll().stream()
                .map(c -> c.getClaimName())
                .collect(Collectors.toSet());
        return oidcIdentifierRequest.createFilteredRequest(Set.copyOf(claimNames));
    }
}
