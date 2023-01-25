--liquibase formatted sql

--changeset author:srees

/* View that provides information about subscribers and associated OIDC identifiers. */

CREATE VIEW subscriber_oidc_identifier AS
SELECT s.id AS subscriber_id, s.subscriber_name, i.id AS oidc_identifier_id, i.issuer, c.claim_name, c.claim_value
FROM subscriber s
LEFT OUTER JOIN oidc_identifier i ON i.subscriber_id = s.id
LEFT OUTER JOIN oidc_identifier_claim c ON c.oidc_identifier_id = i.id
ORDER BY s.id, i.id, c.id
;
