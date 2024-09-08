--liquibase formatted sql

--changeset christian.bentley:1

CREATE TABLE book (
    id uuid PRIMARY KEY,
    title varchar(255),
    author varchar(255)
)