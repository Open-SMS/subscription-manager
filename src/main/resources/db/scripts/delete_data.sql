/**
 * This script deletes all data from the system.
 *
 * Use with great care!
 */

BEGIN;

/** Subscription **/
DELETE FROM subscription;

/** Subscriber **/
DELETE FROM saml_identifier;
DELETE FROM oidc_identifier_claim;
DELETE FROM oidc_identifier;
DELETE FROM subscriber;

/** Claim name **/
DELETE FROM claim_name;

\echo 'commit transaction?'
