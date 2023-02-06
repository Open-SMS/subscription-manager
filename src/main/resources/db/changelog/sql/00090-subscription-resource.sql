--liquibase formatted sql

--changeset author:srees

CREATE TABLE subscription_resource (
    id SERIAL PRIMARY KEY,
    resource_uri VARCHAR(1000) NOT NULL,
    resource_description VARCHAR(1000) NOT NULL
);

CREATE UNIQUE INDEX subscription_resource_uq_resource_uri ON subscription_resource (resource_uri);
