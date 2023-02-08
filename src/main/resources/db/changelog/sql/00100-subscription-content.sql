--liquibase formatted sql

--changeset author:srees

CREATE TABLE subscription_content (
    id SERIAL PRIMARY KEY,
    subscription_resource_id BIGINT NOT NULL,
    CONSTRAINT subscription_content_fk_subscription_resource_id
        FOREIGN KEY (subscription_resource_id) REFERENCES subscription_resource (id)
);

CREATE INDEX subscription_content_ix_subscription_resource_id ON subscription_content (subscription_resource_id);
