--liquibase formatted sql

--changeset author:srees

CREATE TABLE content_identifier (
    id SERIAL PRIMARY KEY,
    subscription_resource_id BIGINT NOT NULL,
    CONSTRAINT content_identifier_fk_subscription_resource_id
        FOREIGN KEY subscription_resource_id REFERENCES subscription_resource (id)
);

CREATE INDEX content_identifier_ix_subscription_resource_id ON content_identifier (subscription_resource_id);
