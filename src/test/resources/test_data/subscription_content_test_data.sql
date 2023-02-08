/**
 * Test data involving subscription content. Note, this script depends upon subscription_resource_test_data.sql
 * having be previously executed.
 */

/* Subscription resource: https://example.com */
INSERT INTO subscription_content (id, subscription_resource_id) VALUES (100000001, 100000001);

/* Subscription resource: https://universal-reference.com/music */
INSERT INTO subscription_content (id, subscription_resource_id) VALUES (100000002, 100000002);

/* Subscription resource: https://universal-reference.com/economics */
INSERT INTO subscription_content (id, subscription_resource_id) VALUES (100000003, 100000003);

/* Subscription resource: urn:zodiac2000.co.uk:data */
INSERT INTO subscription_content (id, subscription_resource_id) VALUES (100000004, 100000004);
INSERT INTO subscription_content (id, subscription_resource_id) VALUES (100000005, 100000004);
INSERT INTO subscription_content (id, subscription_resource_id) VALUES (100000006, 100000004);
