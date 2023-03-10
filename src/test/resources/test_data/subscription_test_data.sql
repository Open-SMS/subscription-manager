/**
 * Test data for subscription integration tests. Note this data depends on the subscriber data in
 * subscriber_test_data.sql, subscription_content_test_data.sql and subscription_resource_test_data.sql
 * existing in the database.
 */

/** Subscriptions for Daphne Oram (100000001) */
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, subscription_content_id, subscriber_id)
    VALUES (100000001, '2012-01-01', '2012-12-31', 'f', 'f', 100000002, 100000001);

/** Subscriptions for Cardiff University (100000004). Subscriptions to content 100000001 and 100000002 are active
    at the standard test time. The subscription to 100000003 has an end date in the past so inactive. */
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, subscription_content_id, subscriber_id)
    VALUES (100000002, '2012-01-01', '2012-12-31', 'f', 'f', 100000001, 100000004);
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, subscription_content_id, subscriber_id)
    VALUES (100000003, '2012-01-01', '2012-12-31', 'f', 'f', 100000002, 100000004);
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, subscription_content_id, subscriber_id)
    VALUES (100000004, '2011-01-01', '2011-12-31', 'f', 'f', 100000003, 100000004);

/** Subscriptions for The Open University (100000005). One subscription to 100000004 is active while the other
    is inactive as a result of the subscription periods. */
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, subscription_content_id, subscriber_id)
    VALUES (100000005, '2011-01-01', '2011-12-31', 'f', 'f', 100000004, 100000005);
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, subscription_content_id, subscriber_id)
    VALUES (100000006, '2012-01-01', '2012-12-31', 'f', 'f', 100000004, 100000005);

/** Subscriptions for University of Brighton (100000008). One subscription to 100000001 is inactive as a result of
    being terminated although the subscription period includes the test date. One subscription to 100000002 is
    active as a result of having a start date equal to the test date. One subscription to CONTENT-3 is
    active as a result of having an end date equal to the test date. */
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, subscription_content_id, subscriber_id)
    VALUES (100000007, '2012-01-01', '2012-12-31', 't', 't', 100000001, 100000008);
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, subscription_content_id, subscriber_id)
    VALUES (100000008, '2012-06-03', '2012-12-31', 'f', 'f', 100000002, 100000008);
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, subscription_content_id, subscriber_id)
    VALUES (100000009, '2012-01-01', '2012-06-03', 'f', 'f', 100000003, 100000008);

/** Subscriptions for University of Brighton Admin (100000009). One subscription to 100000002 is active because
    the start  date is empty and the end date is after the test date. One subscription to 100000003 has an empty
    start date but is inactive because the end date is before the test date. */
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, subscription_content_id, subscriber_id)
    VALUES (100000010, NULL, '2012-12-31', 'f', 'f', 100000002, 100000009);
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, subscription_content_id, subscriber_id)
    VALUES (100000011, NULL, '2012-06-02', 'f', 'f', 100000003, 100000009);

/** Subscriptions for Vince Clarke (100000010). One subscription to content 100000001 is active because
    the start  date is before the test date and the end date is empty. One subscription to 100000002 has an empty
    end date but is inactive because the start date is after the test date. One subscription to 100000003
    is inactive because the subscription is suspended even though the test date is between the start and end dates */
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, subscription_content_id, subscriber_id)
    VALUES (100000012, '2012-01-01', NULL, 'f', 'f', 100000001, 100000010);
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, subscription_content_id, subscriber_id)
    VALUES (100000013, '2012-06-04', NULL, 'f', 'f', 100000002, 100000010);
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, subscription_content_id, subscriber_id)
    VALUES (100000014, '2012-01-01', '2012-12-31', 'f', 't', 100000003, 100000010);
