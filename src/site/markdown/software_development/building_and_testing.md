# Building and Testing Subscription Manager

## Building

The application should be built using Maven. For example
```
mvn clean verify
```
will compile the application, run all unit and integration tests, and build the artifact.

The Maven `site` goal produces reports and the site documentation.
```
mvn clean verify site
```

## Testing

The unit tests are executed by the Maven Surefire plugin which is bound to the test phase.

The integration tests are executed by the Maven Failsafe plugin which is bound to the verify phase. Executing
the integration tests requires a PostgreSQL database server to be available, and accepting connections
as specified in `application-integration.properties`. A database and role should be created for performing tests. The
schema is created and managed by the application using Liquibase.

Execute the following commands as a PostgreSQL superuser.
```
CREATE ROLE subscription_manager_test LOGIN PASSWORD 'test';
CREATE DATABASE subscription_manager_test OWNER = 'subscription_manager_test';
```

Test coverage reports are created as part of the `site` goal.
