--liquibase formatted sql

--changeset author:srees

CREATE TABLE subscription (
    id SERIAL PRIMARY KEY,
    start_date DATE,
    end_date DATE,
    terminated BOOLEAN NOT NULL DEFAULT FALSE,
    content_identifier VARCHAR(100) NOT NULL,
    subscriber_id BIGINT NOT NULL,
    CONSTRAINT subscription_fk_subscriber_id FOREIGN KEY (subscriber_id) REFERENCES subscriber (id)
);

CREATE INDEX subscription_ix_subscriber_id ON subscription (subscriber_id);

