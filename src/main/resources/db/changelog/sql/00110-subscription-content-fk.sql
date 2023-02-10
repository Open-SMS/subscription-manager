--liquibase formatted sql

--changeset author:srees

ALTER TABLE subscription DROP COLUMN content_identifier;

ALTER TABLE subscription ADD COLUMN subscription_content_id BIGINT NOT NULL;
ALTER TABLE subscription ADD CONSTRAINT subscription_fk_subscription_content_id FOREIGN KEY (subscription_content_id)
    REFERENCES subscription_content (id);
CREATE INDEX subscription_ix_subscription_content_id ON subscription (subscription_content_id);
