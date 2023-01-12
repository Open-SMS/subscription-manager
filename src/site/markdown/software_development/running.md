# Running the Subscription Manager Application

## Application Server

The subscription manager application is a Spring-Boot application which includes an embedded Tomcat application server.
Executing `uk.co.zodiac2000.subscriptionmanager.SubscriptionManagerApplication` should start the server.
See [Embedded Web Servers](https://docs.spring.io/spring-boot/docs/2.0.6.RELEASE/reference/html/howto-embedded-web-servers.html)
for more details.

## Database

The application uses PostgreSQL as the persistence store. It has been tested with version 14.6, but is likely to
work with other supported versions.

A database and role need to be created before running the application. The connection details are defined in
`application.properties`. To create a database suitable for the default connection details execute the following
commands as a PostgreSQL superuser.
```
CREATE ROLE subscription_manager LOGIN PASSWORD 'test';
CREATE DATABASE subscription_manager OWNER = 'subscription_manager';
```

*Note* that the password should be changed for production deployments!
