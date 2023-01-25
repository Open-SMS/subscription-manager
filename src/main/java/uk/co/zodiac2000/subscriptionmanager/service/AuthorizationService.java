package uk.co.zodiac2000.subscriptionmanager.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriber.Subscriber;
import uk.co.zodiac2000.subscriptionmanager.factory.SubscriberResponseDtoFactory;
import uk.co.zodiac2000.subscriptionmanager.repository.SubscriberRepository;
import uk.co.zodiac2000.subscriptionmanager.specification.SubscriberSpecifications;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierRequestDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SubscriberResponseDto;

/**
 * Service responsible for identifying subscribers where the authorization identification requirements
 * are satisfied by the authentication identifiers supplied by the client.
 */
@Service
@Transactional(readOnly = true)
public class AuthorizationService {

    @Autowired
    private ClaimNameService claimNameService;

    @Autowired
    private SubscriberSpecifications subscriberSpecifications;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SubscriberResponseDtoFactory subscriberResponseDtoFactory;

    /**
     * Returns subscribers that are associated with an OIDC identifier where the issuer and all the OIDC claims match
     * the values of those fields in {@code oidcIdentiferRequest}. Where the request contains multiple values
     * then a match occurs if any value matches the value associated with the subscriber's OIDC identifier.
     * <br><br>
     * For example, if a Subscriber's associated OIDC identifiers are:
     * <pre>
     * {@code [
     *  {
     *   issuer: "http://example.com",
     *   oidcIdentifierClaims: [
     *    {claimName: "groups", claimValue: "staff"},
     *    {claimName: "fs-site", claimValue: "Brighton"}
     *   ]
     *  },
     *  {
     *   issuer: "http://example.com",
     *   oidcIdentifierClaims: [
     *    {claimName: "groups", claimValue: "admin"}
     *   ]
     *  }
     * ] }
     * </pre>
     * This request representing the claims submitted by a client will match the subscriber because it
     * includes the claims required to match the first OIDC identifier. Note that the {@code sub} claim was not
     * required to identify the subscriber.
     * <pre>
     * {@code {
     *  issuer: "http://example.com",
     *  oidcIdentifierClaims: [
     *   {
     *    claimName: "groups",
     *    claimValues: ["staff", "member"]
     *   },
     *   {
     *    claimName: "fs-site",
     *    claimValues: ["Brighton"]
     *   },
     *   {
     *    claimName: "sub",
     *    claimValues: ["43792473"]
     *   }
     *  ]
     * } }
     * </pre>
     * This request representing the claims submitted by a client will match the subscriber because it
     * includes the claims required to match the second OIDC identifier.
     * <pre>
     * {@code {
     *  issuer: "http://example.com",
     *  oidcIdentifierClaims: [
     *   {
     *    claimName: "groups",
     *    claimValues: ["staff", "admin"]
     *   }
     *  ]
     * } }
     * </pre>
     * This request representing the claims submitted by a client won't match the subscriber because it
     * doesn't match the {@code fs-site} claim.
     * <pre>
     * {@code {
     *  issuer: "http://example.com",
     *  oidcIdentifierClaims: [
     *   {
     *    claimName: "groups",
     *    claimValues: ["staff", "student"]
     *   },
     *   {
     *    claimName: "fs-site",
     *    claimValues: ["Shoreham"]
     *   }
     *  ]
     * } }
     * </pre>
     * <i>Note, the representations used above are to illustrate how a subscriber is identifier and are not exactly
     * the same as the object model.</i>
     * @param oidcIdentifierRequest request DTO the contains the issuer and claims submitted by the client
     * @return zero or more matching subscribers
     */
    public Set<SubscriberResponseDto> getSubscribersByOidcIdentifiers(
            final OidcIdentifierRequestDto oidcIdentifierRequest) {
        // Remove claims from the request where the claim name is unknown to the system.
        OidcIdentifierRequestDto filteredRequest = this.claimNameService.filterClaimsRequest(oidcIdentifierRequest);

        // Retrieve subscribers where issuer and an associated claim match the issuer and one claim in the request.
        Specification<Subscriber> querySpecification
                = this.subscriberSpecifications.oidcAuthorizationCriteria(filteredRequest);
        List<Subscriber> subscribers = this.subscriberRepository.findAll(querySpecification);

        // Filter retrieved subscribers removing those where all required claims are not satisfied by the request.
        List<Subscriber> identifiedSubscribers = subscribers.stream()
                .filter(s -> s.claimsSatisfyRequirements(oidcIdentifierRequest))
                .collect(Collectors.toList());

        return this.subscriberResponseDtoFactory.subscribersToSubscriberResponseDtos(Set.copyOf(identifiedSubscribers));
    }
}
