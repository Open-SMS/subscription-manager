--liquibase formatted sql

--changeset author:srees

CREATE TABLE oidc_identifier (
    id SERIAL PRIMARY KEY,
    issuer VARCHAR(1000) NOT NULL,
    subject VARCHAR(1000) NOT NULL,
    subscriber_id INTEGER NOT NULL,
    CONSTRAINT oidc_identifier_fk_subscriber_id FOREIGN KEY (subscriber_id) REFERENCES subscriber(id)
);

CREATE INDEX oidc_identifier_ix_subscriber_id ON oidc_identifier (subscriber_id);
CREATE INDEX oidc_identifier_ix_issuer_subject ON oidc_identifier (issuer, subject);

