# Subscriber Identification

The following document describes how subscribers are identified as a result of information from OIDC authentication
supplied by a client.

## Requirements

The claim names that might be included in an authorization request are unknown. It is expected that an RP may forward
all the claims it receives or some arbitrary subset of them. The `iss` (issuer) and `sub` (subject) claims should
always be included, although see note about OpenAthens Keystone and `sub` stability. An authorization request is
required to include the issuer and at least one claim.

A subscriber is identified when an OIDC identifier associated with a subscriber has the same issuer, and all required
claims associated with the OIDC identifier are matched. A subscriber may be associated with multiple OIDC identifiers
or other authentication identifiers, any of which could identify the subscriber.

The required claim names and associated values required by the system to identify a subscriber by OIDC identifier are
also unknown. The only restriction is that each OidcIdentifier must be associated with at least one required claim.
There may be multiple required claims with the same claim name but different values associated with an OIDC identifier.
All of these claims must be matched.

Although standard claim names exist, it is very common for non-standard claim names to be used. The claim names used by
OpenAthens Keystone don't even exist in the IANA registration document.

For example, the claims provided in the authorization request may be:
```
{"iss": "https://free.ac.uk"}
{
  "groups": [ "staff", "member" ],
  "sub": "bKMPRFo6L1ZqYNZ3",
  "fc-user": "F+bThf+i0oo8K"
}
```
and subscriber "Free College" requires the following for authorization
```
iss = "https://free.ac.uk" AND groups = "member"
```

This would identify the subscriber based on the `iss` and `groups` claims presented by the client.

See [API docs AuthorizationService](../apidocs/uk/co/zodiac2000/subscriptionmanager/service/AuthorizationService.html)
for more examples.

## Implementation

The problem these requirements present is that identifying the subscriber is not possible with any data model and
simple query that is based upon the claims provided in the authorization request. The code creating the query does not
know that any value of `sub` would be allowed to identify Free College.

The initial implementation algorithm is:

* Remove claims for all claim names that don't exist in the system.
* Perform a query that returns subscribers that match `iss` and each claim.
* Check each remaining subscriber to determine whether the presented claims would meet its authorization requirements.

In detail, using the example above this would result in the following actions.

Remove claim with claim name `fc-user` because it is unknown to the system.

Query subscribers
```
SELECT DISTINCT subscriber
FROM subscriber s
INNER JOIN oidc_identifier oi ON s.id = oi.subscriber_id
INNER JOIN oidc_identifier_claim oc ON oi.id = oc.oidc_identifier_id
WHERE (oc.claim_name = 'groups'
       AND oc.claim_value = 'staff'
       OR oc.claim_name = 'groups'
       AND oc.claim_value = 'member'
       OR oc.claim_name = 'sub'
       AND oc.claim_value = 'bKMPRFo6L1ZqYNZ3')
  AND oi.issuer = 'https://free.ac.uk'
```

Filter subscribers by calling a method with the provided claims that returns true if all required claims are matched.

The performance of this approach in various scenarios should be considered, along with alternative approaches that
may be more efficient.

# Supporting Information

## Standard Claims

Claim names that are a standard part of the OIDC specification.
See [Standand Claims](https://openid.net/specs/openid-connect-core-1_0.html#StandardClaims)

It is recommended to use the `sub` claim to reliably identify a user as recommended by
[Claim Stability and Uniqueness](https://openid.net/specs/openid-connect-core-1_0.html#ClaimStability).
It is possible that the `email` claim may be required with caveats about stability and uniqueness.

## IANA Registered Claims

Claim names that have been registered with IANA.
See [JSON Web Token Claims](https://www.iana.org/assignments/jwt/jwt.xhtml)

I need to look through this list in more detail. It looks like `groups`, `roles` and `entitlements` might be of use.

## NHS Care Identity Authentication

Claims that are supported by NHS Care Identity Authentication.
See [Scopes and Claims](https://digital.nhs.uk/services/identity-and-access-management/nhs-care-identity-service-2/care-identity-authentication/guidance-for-developers/detailed-guidance/scopes-and-claims)

The `profile` scope includes a `uid` claim, but the documentation indicates that this is identical to `sub`, so may
not be relevant as an alternative claim.
See [Claims](https://digital.nhs.uk/services/identity-and-access-management/nhs-care-identity-service-2/care-identity-authentication/guidance-for-developers/detailed-guidance/scopes-and-claims#claims)

## Okta / Auth0

https://auth0.com/docs/secure/tokens/json-web-tokens/json-web-token-claims

## OpenAthens Keystone

This is a Shibboleth / OIDC bridge. [See](https://docs.openathens.net/providers/what-is-openathens-keystone).

The mapping of SAML attributes to OIDC claims is described
[here](https://docs.openathens.net/providers/mapping-saml-attributes-to-oidc-claims). 

Interestingly, they state that `sub` is "a non-persistent user identifier", which conflicts with some of the docs
about claim stability which state that it is a stable identifier.

Derivations of eduPersonScopedAffiliation are provided as `derivedEduPersonAffiliation` and `derivedEduPersonScope`
containing just the affiliation and scope respectively.

Claims may be multi-valued, e.g. `{ "derivedEduPersonScope" : [ "student", "staff"] }`
