# Subscriber Identification

The requirements for subscriber identification from OIDC Claims are being explored by
[Issue #30](https://github.com/Open-SMS/subscription-manager/issues/30). The following document contains a summary
of the requirements identified so far, and initial implementation ideas.

## Requirements

The claim names that might be included in an authorization request are unknown. It is expected that an RP may forward
all the claims it receives or some arbitrary subset of them. The `iss` (issuer) and `sub` (subject) claims should
always be included, although see note about OpenAthens Keystone and `sub` stability.

The required claim names and associated values required by the system to identify a subscriber are also unknown.
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

## Implementation

The problem these requirements present is that identifying the subscriber is not possible with any data model and
simple query that is based upon the claims provided in the authorization request. The code creating the query does not
know that any value of `sub` would be allowed to identify Free College.

An initial implementation idea might be:

* Remove claims for all claim names that don't exist in the system.
* Perform a query that returns subscribers that match `iss` and each claim.
* Check each remaining subscriber to determine whether the presented claims would meet its authorization requirements.

In detail, using the example above this would result in the following actions.

Remove claim with claim name `fc-user` because it is unknown to the system.

Query subscribers (pseudo SQL)
```
SELECT DISTINCT subscriber
FROM subscriber_claim_requirements
WHERE iss = 'https://free.ac.uk' AND claim_name = 'groups' AND claim_value = 'staff'
OR iss = 'https://free.ac.uk' AND claim_name = 'groups' AND claim_value = 'member'
OR iss = 'https://free.ac.uk' AND claim_name = 'sub' AND claim_value = 'bKMPRFo6L1ZqYNZ3'
```

Filter subscribers by calling a method with the provided claims that returns true if the claims meet its requirements.

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

Claims may be multi-valued, e.g. `{ "claimName" : [ "value1", "value2"] }`
