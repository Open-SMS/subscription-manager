# Subscription Manager

The Subscription Manager is a component of the planned Subscription Management System.
It is responsible for modelling subscribers and the subscriptions associated with those subscribers. It provides a
REST interface for managing and retrieving information about subscribers and subscriptions.

The Subscription Manager is intended to be used by a resource that allows access to content by virtue of the user
being identified as a subscriber that has an active subscription to the defined content. For example, a website
may only allow access to a collection of reports to certain users.

Initially subscribers may be identified by OIDC Claims or SAML Attribute assertions. An Authorization Server or
Identity Provider may use any available method to authenticate the user. The resource uses an RP (OIDC)
or SP (SAML) to collect the claims or assertions provided by the Authorization Server and use that information
in an authorization request made to the Subscription Manager REST API. In future it is intended that subscribers may
also be identified by IP address.

# Design Overview

This application is designed as a simple microservice that will form part of a larger application.

The overall design makes use of Domain Driven Design (DDD) principles, domain modelling and object orientated
design.

The design is implemented using Spring Framework and JPA 2.1. The persistence store is PostgreSQL.

See [Ubiquitous Language](ubiquitous_language.html) for descriptions of the terms used in the domain model.

