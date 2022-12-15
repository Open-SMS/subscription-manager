/**
 * Test data for Subscriber integration tests.
 */

INSERT INTO subscriber (id, subscriber_name) VALUES (100000001, 'Daphne Oram');
INSERT INTO oidc_identifier (id, issuer, subject, subscriber_id) VALUES (100000001, 'https://www.facebook.com', '23987283', 100000001);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000002, 'Richard Feynman');

INSERT INTO subscriber (id, subscriber_name) VALUES (100000003, 'Barbara Castle');

INSERT INTO subscriber (id, subscriber_name) VALUES (100000004, 'Cardiff University');
INSERT INTO saml_identifier (id, entity_id, scoped_affiliation, subscriber_id)
    VALUES (100000001, 'https://idp.cardiff.ac.uk/shibboleth', 'staff@idp.cardiff.ac.uk', 100000004);
INSERT INTO saml_identifier (id, entity_id, scoped_affiliation, subscriber_id)
    VALUES (100000002, 'https://idp.cardiff.ac.uk/shibboleth', 'member@idp.cardiff.ac.uk', 100000004);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000005, 'The Open University');
INSERT INTO oidc_identifier (id, issuer, subject, subscriber_id) VALUES (100000002, 'https://oidc.open.ac.uk', '343274', 100000005);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000006, 'Klaus Schulze');
INSERT INTO oidc_identifier (id, issuer, subject, subscriber_id) VALUES (100000003, 'https://www.facebook.com', '1241482', 100000006);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000007, 'Klaus Schulze (work)');
INSERT INTO oidc_identifier (id, issuer, subject, subscriber_id) VALUES (100000004, 'https://www.facebook.com', '1241482', 100000007);
INSERT INTO oidc_identifier (id, issuer, subject, subscriber_id) VALUES (100000005, 'https://accounts.google.com', '9348', 100000007);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000008, 'University of Brighton');
INSERT INTO oidc_identifier (id, issuer, subject, subscriber_id) VALUES (100000006, 'https://oidc.brighton.ac.uk', '21224', 100000008);
INSERT INTO saml_identifier (id, entity_id, scoped_affiliation, subscriber_id)
    VALUES (100000003, 'https://brighton.ac.uk/shib', 'staff@brighton.ac.uk', 100000008);
INSERT INTO saml_identifier (id, entity_id, scoped_affiliation, subscriber_id)
    VALUES (100000004, 'https://brighton.ac.uk/shib', 'student@brighton.ac.uk', 100000008);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000009, 'University of Brighton Admin');
INSERT INTO saml_identifier (id, entity_id, scoped_affiliation, subscriber_id)
    VALUES (100000005, 'https://brighton.ac.uk/shib', 'staff@brighton.ac.uk', 100000009);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000010, 'Vince Clarke');
INSERT INTO oidc_identifier (id, issuer, subject, subscriber_id) VALUES (100000007, 'https://www.facebook.com', '382022', 100000010);

INSERT INTO subscriber (id, subscriber_name) VALUES (100000011, 'Suzanne Ciani');
INSERT INTO oidc_identifier (id, issuer, subject, subscriber_id) VALUES (100000008, 'https://www.facebook.com', '21481', 100000011);
INSERT INTO saml_identifier (id, entity_id, scoped_affiliation, subscriber_id)
    VALUES (100000006, 'https://mit.edu/shib', 'member@mit.edu', 100000011);
