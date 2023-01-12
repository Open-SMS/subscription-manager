# Software Design Outline

## Domain Driven Design

This application makes use of Domain Driven Design principles. Each part of the software is assigned specific roles
for implementing the requirements.

Key roles are:

### Domain Model

The domain model is represented by classes in the `uk.co.zodiac2000.subscriptionmanager.domain` package. These
classes represent aggregate root entities and their associated value objects. All changes to the state of an aggregate
are performed by calling methods in the aggregate root.

Ideally all business logic should be implemented in the domain model.

### Repositories

Repositories provide access to the persistence layer. Each aggregate root has an associated repository to retrieve,
persist and delete aggregate roots.

### Factories

Factories are responsible for converting information in the domain model into an external representation of that
information, and for creating new aggregate roots from information provided to the application.

### DTOs

DTOs are value objects that are external representations of some aspect of the domain model. Some elements of
[CQRS](https://martinfowler.com/bliki/CQRS.html) are used, in particular there are three general groups of DTOs.

* Response DTOs. These provide an external representation of some information about the domain model.
* Request DTOs. These represent a request to the application to provide some information about the domain model.
* Command DTOs. These represent a request to the application to make a change to the state of the domain model.

### Service Facade

The service facade is responsible for cross-cutting concerns including transaction management and authorization.
Service facade classes have access to the repositories and factories in the Spring context, and work with those
components to make requests to aggregate roots to change the domain model, and to obtain information
about the domain model.

While the service facade may contain application logic, in general business logic should be implemented in the domain
model. Complex procedural code in the service facade should be avoided.

## Presentation Layer / API

### REST API

A REST API is implemented using Spring MVC. Requests made to the API are delegated to the service facade. The API
is responsible for handling HTTP requests, serializing and deserializing external representations of DTOs, and
handling validation errors.

### Validation

Where required, DTOs are validated by the presentation layer using [JSR 380](https://jcp.org/en/jsr/detail?id=380)
Java Bean Validation. Custom constraints are in the `uk.co.zodiac2000.subscriptionmanager.constraint` package.
When validation fails the Spring `MethodArgumentNotValid` exception should be translated into a response document
that allows the user to understand and potentially correct the error.

Validation is intended to improve the user experience by accurately reporting why an operation cannot be carried out
because of invalid data. It is not intended to ensure the domain model remains valid which is entirely the
responsibility of the domain model.

