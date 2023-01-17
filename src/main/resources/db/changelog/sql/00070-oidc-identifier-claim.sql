--liquibase formatted sql

--changeset author:srees

ALTER TABLE oidc_identifier DROP COLUMN subject;

CREATE TABLE oidc_identifier_claim (
    id SERIAL PRIMARY KEY,
    claim_name VARCHAR(1000) NOT NULL,
    claim_value VARCHAR(1000) NOT NULL,
    oidc_identifier_id BIGINT NOT NULL,
    CONSTRAINT oidc_identifier_claim_fk_oidc_identifier_id
        FOREIGN KEY (oidc_identifier_id) REFERENCES oidc_identifier(id),
    CONSTRAINT oidc_identifier_claim_fk_claim_name
        FOREIGN KEY (claim_name) REFERENCES claim_name(claim_name)
);

CREATE UNIQUE INDEX oidc_identifier_claim_uq_claim_name_value
    ON oidc_identifier_claim (claim_name, claim_value, oidc_identifier_id);

CREATE INDEX oidc_identifier_claim_ix_oidc_identifier_id ON oidc_identifier_claim (oidc_identifier_id);

CREATE INDEX oidc_identifier_claim_ix_claim_name ON oidc_identifier_claim (claim_name);
