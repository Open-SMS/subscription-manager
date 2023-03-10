# Ubiquitous Language

## Subscriber

A subscriber is an individual or organization that may be associated with subscriptions that provide access to
resources. A subscriber may be identified for authorization by matching OIDC Claims or SAML Attributes with the
authentication identifiers associated with that subscription.

### Authentication Identifier

An authentication identifier represents the claims, assertions or other information that identifies a subcriber.
The resource requesting authorization from the Subscription Manager provides any claims or assertions it has
obtained about a user to the API which are then matched against the authentication identifiers present in the system
to identify subscribers.

## Subscription

A active subscription indicates permission to access a resource defined by a content identifier. A subscription may be
active or inactive depending on the period the subscription is for, or whether the subscription has been suspended.
When the current date is outside the subscription period, or the subscription has been suspended, the subscription
is inactive.

### Suspended subscription

A subscription may be suspended subject to the rules for suspending subscriptions by making a request to the API. When
suspended the subscription is inactive. A suspended subscription may be unsuspended in certain circumstances.

### Terminated Subscription

A subscription may be terminated subject to the rules for terminating subscription by making a request to the API.
This action suspends the subscription and prevents it being unsuspended. Terminating a subscription indicates that it
will never become active in the future.

## Subscription Resource

A subscription resource defines the resource that subscriptions are associated with, for example a website or part of
a website that provides content available to subscribers. A subscription resource is uniquely identified within the
system by its resource URI. This may be the website URL or any other identifier. When a request is made to determine
authorized access to content for an identified subscriber the request is made in the context of a subscription
resource, and only subscription content identifiers associated with that subscription resource are returned.

## Subscription Content

Subscription content defines the content that an active subscription provides access to. Subscription content is
exists within the context of a subscription resource, and includes one or more content identifiers that can be used
to provide fine-grained access control to specific content within the resource. The target of a subscription within
the system is subscription content. An active subscription indicates permission to access the content described by
the associated subscription content.

## Content Identifier

Subscription content includes one or more content identifiers which allow fine-grained access control to specific
content within a subscription resource.
