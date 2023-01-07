--liquibase formatted sql

--changeset author:srees

ALTER TABLE subscription
ADD COLUMN suspended BOOLEAN NOT NULL DEFAULT FALSE;


