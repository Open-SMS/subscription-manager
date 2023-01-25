package uk.co.zodiac2000.subscriptionmanager.specification;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.OidcIdentifier;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.OidcIdentifierClaim;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.Subscriber;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierRequestDto;

/**
 * Produces Specification objects used for retrieving Subscriber objects from the repository.
 */
@Component
public class SubscriberSpecifications {

    /**
     * Return a Specification that selects Subscriber objects associated with OidcIdentifier objects where the issuer
     * matches the issuer in the request, and a claim matches one of the claims in the request. If the request
     * has multiple claims then subscribers are returned that match any claim.
     * @param request the request
     * @return the specification
     */
    public Specification<Subscriber> oidcAuthorizationCriteria(
            final OidcIdentifierRequestDto request) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);

            Join<Subscriber, OidcIdentifier> oidcIdentifierJoin = root.join("oidcIdentifiers");
            Join<OidcIdentifier, OidcIdentifierClaim> oidcIdentifierClaimJoin
                    = oidcIdentifierJoin.join("oidcIdentifierClaims");

            List<Predicate> claimsPredicates = new ArrayList<>();
            request.getOidcIdentifierClaims().stream()
                    .forEach(c -> c.getClaimValuesAsStrings().stream()
                        .forEach(v -> claimsPredicates.add(criteriaBuilder.and(
                            criteriaBuilder.equal(oidcIdentifierClaimJoin.get("claimName"), c.getClaimName()),
                            criteriaBuilder.equal(oidcIdentifierClaimJoin.get("claimValue"), v)
                        ))
                    ));

            return criteriaBuilder.and(
                    criteriaBuilder.or(claimsPredicates.toArray(Predicate[]::new)),
                    criteriaBuilder.equal(oidcIdentifierJoin.get("issuer"), request.getIssuer())
            );
        };
    }
}
