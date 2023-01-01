# Open SMS Subscription Manager Service

This application is a subscription manager service that provides information about
active subscriptions associated with externally identified subscribers.

It provides a REST API to manage subscribers and subscriptions, and to provide information
about access rights to protected resources.

# Ubiquitous Language

## Subscription

A subscription represents the rights to access a specified resource. A subscription may be *active*
which indicates right of access. An *inactive* subscription may have provided access rights in the
past or will do in the future.

## Subscriber

A subscriber represents something that may be entitled to access resources as a result of having
an active subscription. A subscriber could be an individual, an organization, or any other group
of people that can be identified.

## Subscriber Identifier

A means of identifying an subscriber. Initially this will depend on OIDC claims and SAML assertions.

# Project Reports

[Maven project reports](https://open-sms.github.io/subscription-manager/)
