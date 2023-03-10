--liquibase formatted sql

--changeset author:srees

CREATE TABLE subscription_content (
    id SERIAL PRIMARY KEY,
    content_description VARCHAR(1000) NOT NULL,
    subscription_resource_id BIGINT NOT NULL,
    CONSTRAINT subscription_content_fk_subscription_resource_id
        FOREIGN KEY (subscription_resource_id) REFERENCES subscription_resource (id)
);

CREATE INDEX subscription_content_ix_subscription_resource_id ON subscription_content (subscription_resource_id);

CREATE TABLE content_identifier (
    id SERIAL PRIMARY KEY,
    content_identifier VARCHAR(1000) NOT NULL,
    subscription_content_id BIGINT NOT NULL,
    CONSTRAINT content_identifier_fk_subscription_content_id
        FOREIGN KEY (subscription_content_id) REFERENCES subscription_content (id)
);

CREATE INDEX content_identifier_ix_subscription_content_id ON content_identifier (subscription_content_id);
