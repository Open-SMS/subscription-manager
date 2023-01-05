/**
 * Test data for subscription integration tests. Note this data depends on the subscriber data in subscriber_test_data.sql
 * existing in the database.
 */

/** Subscriptions for Daphne Oram (100000001) */
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, content_identifier, subscriber_id)
    VALUES (100000001, '2012-01-01', '2012-12-31', 'f', 'f', 'CONTENT-1', 100000001);

/** Subscriptions for Cardiff University (100000004). Subscriptions to CONTENT-1 and CONTENT-2 are active
    at the standard test time. The subscription to CONTENT-3 has an end date in the past so inactive. */
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, content_identifier, subscriber_id)
    VALUES (100000002, '2012-01-01', '2012-12-31', 'f', 'f', 'CONTENT-1', 100000004);
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, content_identifier, subscriber_id)
    VALUES (100000003, '2012-01-01', '2012-12-31', 'f', 'f', 'CONTENT-2', 100000004);
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, content_identifier, subscriber_id)
    VALUES (100000004, '2011-01-01', '2011-12-31', 'f', 'f', 'CONTENT-3', 100000004);

/** Subscriptions for The Open University (100000005). One subscription to CONTENT-1 is active while the other
    is inactive as a result of the subscription periods. */
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, content_identifier, subscriber_id)
    VALUES (100000005, '2011-01-01', '2011-12-31', 'f', 'f', 'CONTENT-1', 100000005);
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, content_identifier, subscriber_id)
    VALUES (100000006, '2012-01-01', '2012-12-31', 'f', 'f', 'CONTENT-1', 100000005);

/** Subscriptions for University of Brighton (100000008). One subscription to CONTENT-1 is inactive as a result of
    being terminated although the subscription period includes the test date. One subscription to CONTENT-2 is
    active as a result of having a start date equal to the test date. One subscription to CONTENT-3 is
    active as a result of having an end date equal to the test date. */
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, content_identifier, subscriber_id)
    VALUES (100000007, '2012-01-01', '2012-12-31', 't', 't', 'CONTENT-1', 100000008);
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, content_identifier, subscriber_id)
    VALUES (100000008, '2012-06-03', '2012-12-31', 'f', 'f', 'CONTENT-2', 100000008);
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, content_identifier, subscriber_id)
    VALUES (100000009, '2012-01-01', '2012-06-03', 'f', 'f', 'CONTENT-2', 100000008);

/** Subscriptions for University of Brighton Admin (100000009). One subscription to CONTENT-1 is active because
    the start  date is empty and the end date is after the test date. One subscription to CONTENT-2 has an empty
    start date but is inactive because the end date is before the test date. */
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, content_identifier, subscriber_id)
    VALUES (100000010, NULL, '2012-12-31', 'f', 'f', 'CONTENT-1', 100000009);
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, content_identifier, subscriber_id)
    VALUES (100000011, NULL, '2012-06-02', 'f', 'f', 'CONTENT-2', 100000009);

/** Subscriptions for Vince Clarke (100000010). One subscription to CONTENT-1 is active because
    the start  date is before the test date and the end date is empty. One subscription to CONTENT-2 has an empty
    end date but is inactive because the start date is after the test date. */
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, content_identifier, subscriber_id)
    VALUES (100000012, '2012-01-01', NULL, 'f', 'f', 'CONTENT-1', 100000010);
INSERT INTO subscription (id, start_date, end_date, terminated, suspended, content_identifier, subscriber_id)
    VALUES (100000013, '2012-06-04', NULL, 'f', 'f', 'CONTENT-2', 100000010);
