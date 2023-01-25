/**
 * Test data for Subscriber integration tests.
 */

INSERT INTO subscriber (id, subscriber_name) VALUES (100000001, 'Daphne Oram');
INSERT INTO oidc_identifier (id, issuer, subscriber_id) VALUES (100000001, 'https://www.facebook.com', 100000001);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000001, 'sub', '23987283', 100000001);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000002, 'Richard Feynman');

INSERT INTO subscriber (id, subscriber_name) VALUES (100000003, 'Barbara Castle');

INSERT INTO subscriber (id, subscriber_name) VALUES (100000004, 'Cardiff University');
INSERT INTO saml_identifier (id, entity_id, scoped_affiliation, subscriber_id)
    VALUES (100000001, 'https://idp.cardiff.ac.uk/shibboleth', 'staff@idp.cardiff.ac.uk', 100000004);
INSERT INTO saml_identifier (id, entity_id, scoped_affiliation, subscriber_id)
    VALUES (100000002, 'https://idp.cardiff.ac.uk/shibboleth', 'member@idp.cardiff.ac.uk', 100000004);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000005, 'The Open University');
INSERT INTO oidc_identifier (id, issuer, subscriber_id) VALUES (100000002, 'https://oidc.open.ac.uk', 100000005);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000002, 'sub', '343274', 100000002);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000006, 'Klaus Schulze');
INSERT INTO oidc_identifier (id, issuer, subscriber_id) VALUES (100000003, 'https://www.facebook.com', 100000006);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000003, 'sub', '1241482', 100000003);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000007, 'Klaus Schulze (work)');
INSERT INTO oidc_identifier (id, issuer, subscriber_id) VALUES (100000004, 'https://www.facebook.com', 100000007);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000004, 'sub', '1241482', 100000004);
INSERT INTO oidc_identifier (id, issuer, subscriber_id) VALUES (100000005, 'https://accounts.google.com', 100000007);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000005, 'sub', '9348', 100000005);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000008, 'University of Brighton');
INSERT INTO oidc_identifier (id, issuer, subscriber_id) VALUES (100000006, 'https://oidc.brighton.ac.uk', 100000008);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000006, 'sub', '21224', 100000006);
INSERT INTO saml_identifier (id, entity_id, scoped_affiliation, subscriber_id)
    VALUES (100000003, 'https://brighton.ac.uk/shib', 'staff@brighton.ac.uk', 100000008);
INSERT INTO saml_identifier (id, entity_id, scoped_affiliation, subscriber_id)
    VALUES (100000004, 'https://brighton.ac.uk/shib', 'student@brighton.ac.uk', 100000008);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000009, 'University of Brighton Admin');
INSERT INTO saml_identifier (id, entity_id, scoped_affiliation, subscriber_id)
    VALUES (100000005, 'https://brighton.ac.uk/shib', 'staff@brighton.ac.uk', 100000009);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000010, 'Vince Clarke');
INSERT INTO oidc_identifier (id, issuer, subscriber_id) VALUES (100000007, 'https://www.facebook.com', 100000010);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000007, 'sub', '382022', 100000007);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000011, 'Suzanne Ciani');
INSERT INTO oidc_identifier (id, issuer, subscriber_id) VALUES (100000008, 'https://www.facebook.com', 100000011);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000008, 'sub', '21481', 100000008);
INSERT INTO saml_identifier (id, entity_id, scoped_affiliation, subscriber_id)
    VALUES (100000006, 'https://mit.edu/shib', 'member@mit.edu', 100000011);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000012, 'Perching Down University');
INSERT INTO oidc_identifier (id, issuer, subscriber_id) VALUES (100000009, 'https://accounts.google.com', 100000012);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000009, 'sub', '2385258', 100000009);
INSERT INTO oidc_identifier (id, issuer, subscriber_id) VALUES (100000010, 'https://accounts.google.com', 100000012);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000010, 'sub', '234274983', 100000010);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000013, 'Balsdean Library');
INSERT INTO oidc_identifier (id, issuer, subscriber_id)
    VALUES (100000011, 'https://brighton-libraries.gov.uk', 100000013);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000011, 'groups', 'member', 100000011);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000012, 'branch-id', 'balsdean', 100000011);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000014, 'Hartington Road Library');
INSERT INTO oidc_identifier (id, issuer, subscriber_id)
    VALUES (100000012, 'https://brighton-libraries.gov.uk', 100000014);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000013, 'groups', 'member', 100000012);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000014, 'branch-id', 'hartington', 100000012);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000015, 'Westdene Library');
INSERT INTO oidc_identifier (id, issuer, subscriber_id)
    VALUES (100000013, 'https://brighton-libraries.gov.uk', 100000015);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000015, 'groups', 'staff', 100000013);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000016, 'Brighton Libraries Admin');
INSERT INTO oidc_identifier (id, issuer, subscriber_id)
    VALUES (100000014, 'https://brighton-libraries.gov.uk', 100000016);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000016, 'groups', 'hr', 100000014);
INSERT INTO oidc_identifier_claim (id, claim_name, claim_value, oidc_identifier_id)
    VALUES (100000017, 'groups', 'admin', 100000014);
