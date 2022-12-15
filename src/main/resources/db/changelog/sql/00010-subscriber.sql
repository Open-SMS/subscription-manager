--liquibase formatted sql

--changeset author:srees

CREATE TABLE subscriber (
    id BIGSERIAL PRIMARY KEY,
    subscriber_name VARCHAR(200) NOT NULL
);

CREATE UNIQUE INDEX subscriber_uq_subscriber_name ON subscriber(subscriber_name);

