--liquibase formatted sql

--changeset author:srees

CREATE TABLE claim_name (
    id SERIAL PRIMARY KEY,
    claim_name VARCHAR(1000) NOT NULL
);

CREATE UNIQUE INDEX claim_name_uq_subscriber_id ON claim_name (claim_name);
