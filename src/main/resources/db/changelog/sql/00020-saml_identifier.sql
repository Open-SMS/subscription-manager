--liquibase formatted sql

--changeset author:srees

CREATE TABLE saml_identifier (
    id SERIAL PRIMARY KEY,
    entity_id VARCHAR(1000) NOT NULL,
    scoped_affiliation VARCHAR(1000) NOT NULL,
    subscriber_id INTEGER NOT NULL,
    CONSTRAINT saml_identifier_fk_subscriber_id FOREIGN KEY (subscriber_id) REFERENCES subscriber(id)
);

CREATE INDEX saml_identifier_ix_subscriber_id ON saml_identifier (subscriber_id);
CREATE INDEX saml_identifier_ix_entity_id_scoped_affiliation ON saml_identifier (entity_id, scoped_affiliation);
